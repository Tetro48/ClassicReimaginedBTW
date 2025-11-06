package net.tetro48.classicaddon.mixin;

import btw.community.classicaddon.ClassicAddon;
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

	@Shadow private float foodExhaustionLevel;

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
		return this.foodLevel >= getHungerRegenCutoff();
	}
	@ModifyConstant(method = "onUpdate", constant = @Constant(floatValue = 1.33F))
	private float setExhaustionDrainForHunger(float constant) {
		if (ClassicAddon.degranularizeHungerSystem) {
			return 4F;
		}
		return ONE_AND_ONE_THIRD;
	}
	@ModifyConstant(method = "onUpdate", constant = @Constant(floatValue = 0.5F))
	private float setExhaustionDrainForSaturation(float constant) {
		if (ClassicAddon.degranularizeHungerSystem) {
			return 4F;
		}
		return constant;
	}
	@ModifyConstant(method = "onUpdate", constant = @Constant(floatValue = 0.125F))
	private float setSaturationDrain(float constant) {
		if (ClassicAddon.degranularizeHungerSystem) {
			return 1F;
		}
		return constant;
	}
	@Inject(method = "onUpdate", at = @At(value = "FIELD", ordinal = 2, target = "Lnet/minecraft/src/FoodStats;foodLevel:I"))
	private void exhaustionDrainCorrection(EntityPlayer player, CallbackInfo ci) {
		foodExhaustionLevel -= ONE_AND_ONE_THIRD - 1.33F;
		if (ClassicAddon.degranularizeHungerSystem) {
			foodLevel -= 2;
			foodExhaustionLevel -= ONE_AND_ONE_THIRD * 2;
		}
	}
	@Inject(method = "onUpdate", at = @At("RETURN"))
	public void introduceVanillaHealMechanic(EntityPlayer player, CallbackInfo ci) {
		boolean bl = player.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration");
		if (bl && this.foodSaturationLevel > 0.0F && player.shouldHeal() && this.foodLevel >= 60 && ClassicAddon.quickHealToggle) {
			if (this.foodTimer >= ClassicAddon.quickHealTicks) {
				float f = Math.min(Math.max(0.5F, this.foodSaturationLevel), 6.0F);
				player.heal(f / 6.0F);
				this.addExhaustion(f);
				this.foodTimer = 0;
			}
		} else if (bl && (this.foodLevel >= getHungerRegenCutoff()) && player.shouldHeal()) {
			if (this.foodTimer >= 80) {
				player.heal(1.0F);
				this.addExhaustion(ClassicAddon.quickHealToggle ? 6.0F : 3.0F);
				this.foodTimer = 0;
			}
		}
	}
	@Inject(method = "addStats(IF)V", cancellable = true, at = @At(value = "FIELD", target = "Lnet/minecraft/src/FoodStats;foodLevel:I", ordinal = 3))
	public void addFatRegardless(int iFoodGain, float fFatMultiplier, CallbackInfo ci) {
		this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)iFoodGain * fFatMultiplier / 3.0F, this.foodLevel / 3f);
		ci.cancel();
	}
	@Unique
	private static int getHungerRegenCutoff() {
		if (ClassicAddon.intentionalHungerRegenOffset) {
			return 52;
		}
		return 54;
	}
}
