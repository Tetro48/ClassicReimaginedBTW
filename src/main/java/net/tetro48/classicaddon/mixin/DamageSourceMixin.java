package net.tetro48.classicaddon.mixin;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.src.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin {
	@Shadow private float hungerDamage;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void setExhaustion(String par1Str, CallbackInfo ci) {
		if (ClassicAddon.modernExhaustionLevels) {
			this.hungerDamage = 0.1f;
		}
	}
}
