package net.tetro48.classicaddon.mixin;

import btw.block.BTWBlocks;
import emi.dev.emi.emi.api.EmiRegistry;
import emi.dev.emi.emi.api.plugin.BTWPlugin;
import net.minecraft.src.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BTWPlugin.class)
public abstract class EmiBTWPluginMixin {
	@Shadow protected abstract void info(EmiRegistry registry, Block block, String info);

	@Inject(method = "addInfoRecipes", at = @At("HEAD"), remap = false)
	private void addNewInfo(EmiRegistry registry, CallbackInfo ci) {
		this.info(registry, BTWBlocks.workbench, "classicAddon.workbench.info");
	}

}
