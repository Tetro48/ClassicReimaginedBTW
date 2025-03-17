package net.tetro48.classicaddon.mixin;

import btw.util.status.BTWStatusCategory;
import btw.util.status.PlayerStatusEffects;
import btw.util.status.StatusCategory;
import btw.util.status.StatusEffectBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerStatusEffects.class)
public abstract class PlayerStatusEffectsMixin {
    @Shadow
    private static StatusEffectBuilder createExhaustionEffect(int level, StatusCategory category, float effectivenessMultiplier, String name) {
        return null;
    }

    @Inject(method = "createFatEffect", at = @At("RETURN"), cancellable = true, remap = false)
    private static void stopFatEffect(int level, float effectivenessMultiplier, String name, CallbackInfoReturnable<StatusEffectBuilder> cir) {
        cir.setReturnValue(createExhaustionEffect(level, BTWStatusCategory.FAT, effectivenessMultiplier, name).setEvaluator((player) -> false));
    }
}
