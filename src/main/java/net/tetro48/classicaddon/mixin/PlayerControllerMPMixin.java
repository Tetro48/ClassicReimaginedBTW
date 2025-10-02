package net.tetro48.classicaddon.mixin;

import net.minecraft.src.EnumGameType;
import net.minecraft.src.Minecraft;
import net.minecraft.src.PlayerControllerMP;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerControllerMP.class)
public abstract class PlayerControllerMPMixin {
	@Shadow private int blockHitDelay;

	@Shadow private EnumGameType currentGameType;

	@Shadow @Final private Minecraft mc;

	@Inject(method = "clickBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/PlayerControllerMP;onPlayerDestroyBlock(IIII)Z", shift = At.Shift.AFTER))
	private void doNotDelayIfInstamined(int par1, int par2, int par3, int par4, CallbackInfo ci) {
		this.blockHitDelay = 0;
	}

	@Inject(method = "clickBlock", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/EnumGameType;isCreative()Z"))
	private void extinguishFireNoMatterWhat(int par1, int par2, int par3, int par4, CallbackInfo ci) {
		if (!this.currentGameType.isCreative()) {
			this.mc.theWorld.extinguishFire(this.mc.thePlayer, par1, par2, par3, par4);
		}
	}
}
