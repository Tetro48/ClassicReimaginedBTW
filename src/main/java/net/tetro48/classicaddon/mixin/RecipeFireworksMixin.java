package net.tetro48.classicaddon.mixin;

import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RecipeFireworks;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeFireworks.class)
public abstract class RecipeFireworksMixin {
	@Shadow private ItemStack field_92102_a;

	@Inject(method = "matches(Lnet/minecraft/src/InventoryCrafting;Lnet/minecraft/src/World;)Z", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Item;)V", shift = At.Shift.AFTER))
	// this is a backport of the modern change
	private void increaseFireworksOutput(InventoryCrafting par1InventoryCrafting, World par2World, CallbackInfoReturnable<Boolean> cir) {
		this.field_92102_a.stackSize = 3;
	}
}
