package net.tetro48.classicaddon.mixin.client;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiIngameMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public abstract class GuiIngameMenuMixin {
	@Inject(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Minecraft;loadWorld(Lnet/minecraft/src/WorldClient;)V", ordinal = 0))
	public void onLeaveWorld(GuiButton par1GuiButton, CallbackInfo ci) {
		ClassicAddon.isServerRunningThisAddon = false;
	}
}
