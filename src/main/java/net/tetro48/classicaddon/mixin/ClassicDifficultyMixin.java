package net.tetro48.classicaddon.mixin;

import btw.world.util.difficulty.ClassicDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClassicDifficulty.class)
public abstract class ClassicDifficultyMixin {
	@Inject(method = "getNoToolBlockHardnessMultiplier", at = @At("RETURN"), cancellable = true, remap = false)
	public void fasterSpeed(CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(0.15f);
	}
}
