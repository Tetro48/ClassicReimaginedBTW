package net.tetro48.classicaddon.mixin;

import btw.block.BTWBlocks;
import emi.dev.emi.emi.api.EmiRegistry;
import emi.dev.emi.emi.api.plugin.VanillaPlugin;
import emi.dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import emi.dev.emi.emi.api.stack.EmiStack;
import net.minecraft.src.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VanillaPlugin.class)
public abstract class EmiVanillaPluginMixin {
	@Shadow public abstract void register(EmiRegistry registry);

	@ModifyArg(method = "register", at = @At(ordinal = 1, value = "INVOKE", target = "Lemi/dev/emi/emi/api/stack/EmiStack;of(Lnet/minecraft/src/Block;)Lemi/dev/emi/emi/api/stack/EmiStack;"), remap = false)
	private Block modifyCraftingWorkstation(Block block) {
		return BTWBlocks.workbench;
	}
	@Inject(method = "register", at = @At(ordinal = 4, value = "INVOKE", target = "Lemi/dev/emi/emi/api/EmiRegistry;addWorkstation(Lemi/dev/emi/emi/api/recipe/EmiRecipeCategory;Lemi/dev/emi/emi/api/stack/EmiIngredient;)V"), remap = false)
	private void addFurnaceWorkstation(EmiRegistry registry, CallbackInfo ci) {
		registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(Block.furnaceIdle));
	}
}
