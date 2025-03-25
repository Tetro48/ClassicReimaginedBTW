package net.tetro48.classicaddon.mixin;

import net.minecraft.src.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodStats.class)
public abstract class FoodStatsMixin {
    @Shadow private float foodSaturationLevel;

    @Shadow private int foodLevel;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void startWithSomeFat(CallbackInfo ci) {
        this.foodSaturationLevel = 5F;
    }
    @Inject(method = "shouldBurnFatBeforeHunger", at = @At("RETURN"), cancellable = true)
    public void alwaysBurnFat(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.foodSaturationLevel > 0.01f);
    }
    @Inject(method = "addStats(IF)V", cancellable = true, at = @At(value = "FIELD", target = "Lnet/minecraft/src/FoodStats;foodLevel:I", ordinal = 3))
    public void addFatRegardless(int iFoodGain, float fFatMultiplier, CallbackInfo ci) {
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)iFoodGain * fFatMultiplier / 3.0F, this.foodLevel / 3f);
        ci.cancel();
    }
}
