package net.tetro48.classicaddon.mixin.entity;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityAnimal.class)
public abstract class EntityAnimalMixin extends EntityAgeable {
	public EntityAnimalMixin(World par1World) {
		super(par1World);
	}

	@Shadow public abstract void resetHungerCountdown();

	@Shadow public int hungerCountdown;

	@Shadow public abstract void setHungerLevel(int iHungerLevel);

	@Inject(method = "updateHungerState", at = @At("RETURN"))
	private void stopGettingHungry(CallbackInfo ci) {
		if (!ClassicAddon.animageddonToggle) {
			if (hungerCountdown < 24000) {
				resetHungerCountdown();
			}
			setHungerLevel(0);
		}
	}

	@Inject(method = "attemptToEatItem", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityAnimal;addToHungerCount(I)V"))
	private void makeBabyAnimalGrowFaster(ItemStack stack, CallbackInfoReturnable<Boolean> cir, int foodValue) {
		if (this.isChild() && !ClassicAddon.animageddonToggle) {
			addGrowth(foodValue/4);
		}
	}

	@Inject(method = "isReadyToEatLooseFood", at = @At("RETURN"), cancellable = true)
	private void notEatLooseFoodIfChild(CallbackInfoReturnable<Boolean> cir) {
		if (this.isChild() && !ClassicAddon.animageddonToggle) {
			cir.setReturnValue(ClassicAddon.canBabyAnimalEatLooseFood);
		}
	}

	@Inject(method = "panicNearbyAnimals", at = @At("HEAD"), cancellable = true)
	private void noPanickingNearbyAnimals(DamageSource damageSource, CallbackInfo ci) {
		if (!ClassicAddon.animageddonToggle) ci.cancel();
	}

	@Inject(method = "onNearbyPlayerStartles", at = @At("HEAD"), cancellable = true)
	private void noPanicking(EntityPlayer player, CallbackInfo ci) {
		if (!ClassicAddon.animageddonToggle) ci.cancel();
	}
}
