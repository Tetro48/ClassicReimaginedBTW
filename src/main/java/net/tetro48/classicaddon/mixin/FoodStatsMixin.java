package net.tetro48.classicaddon.mixin;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FoodStats;
import net.minecraft.src.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodStats.class)
public abstract class FoodStatsMixin {
    @Unique private final static float ONE_AND_ONE_THIRD = 4f/3f;

    @Shadow private float foodSaturationLevel;

    @Shadow private int foodLevel;

    @Shadow private int foodTimer;

    @Shadow public abstract void addExhaustion(float par1);

    @Inject(method = "<init>", at = @At("TAIL"))
    public void startWithSomeFat(CallbackInfo ci) {
        this.foodSaturationLevel = 5F;
    }
    @Inject(method = "shouldBurnFatBeforeHunger", at = @At("RETURN"), cancellable = true)
    public void alwaysBurnFat(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.foodSaturationLevel > 0.01f);
    }
    @Redirect(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GameRules;getGameRuleBooleanValue(Ljava/lang/String;)Z", ordinal = 0))
    public boolean turnOffBTWHeal(GameRules instance, String par1Str) {
        return this.foodLevel >= 54;
    }
    @ModifyConstant(method = "onUpdate", constant = @Constant(floatValue = 1.33F))
    private float increasePrecision(float constant) {
        return ONE_AND_ONE_THIRD;
    }
    @Inject(method = "onUpdate", at = @At("RETURN"))
    public void introduceVanillaHealMechanic(EntityPlayer player, CallbackInfo ci) {
        boolean bl = player.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration");
        if (bl && this.foodSaturationLevel > 0.0F && player.shouldHeal() && this.foodLevel >= 60) {
            if (this.foodTimer >= 10) {
                float f = Math.min(this.foodSaturationLevel, 6.0F);
                player.heal(f / 6.0F);
                this.addExhaustion(f);
                this.foodTimer = 0;
            }
        } else if (bl && this.foodLevel >= 54 && player.shouldHeal()) {
            if (this.foodTimer >= 80) {
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.foodTimer = 0;
            }
        }
    }
    @Inject(method = "addStats(IF)V", cancellable = true, at = @At(value = "FIELD", target = "Lnet/minecraft/src/FoodStats;foodLevel:I", ordinal = 3))
    public void addFatRegardless(int iFoodGain, float fFatMultiplier, CallbackInfo ci) {
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)iFoodGain * fFatMultiplier / 3.0F, this.foodLevel / 3f);
        ci.cancel();
    }
}
