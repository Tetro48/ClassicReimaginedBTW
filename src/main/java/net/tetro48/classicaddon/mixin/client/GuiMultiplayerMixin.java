package net.tetro48.classicaddon.mixin.client;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.src.GuiMultiplayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public abstract class GuiMultiplayerMixin {
	@Inject(method = "initGui", at = @At("RETURN"))
	private void disableCompassCoordsJustInCase(CallbackInfo ci) {
		ClassicAddon.isServerRunningThisAddon = false;
		ClassicAddon.resetAllSynchronizedPropertyValues();
	}
}
