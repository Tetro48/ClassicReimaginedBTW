package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityAnimal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityAnimal.class)
public abstract class EntityAnimalMixin {
	@Shadow public abstract void resetHungerCountdown();

	@Shadow public int hungerCountdown;

	@Shadow public abstract void setHungerLevel(int iHungerLevel);

	@Inject(method = "updateHungerState", at = @At("RETURN"))
	private void stopGettingHungry(CallbackInfo ci) {
		if (hungerCountdown < 24000) {
			resetHungerCountdown();
		}
		setHungerLevel(0);
	}
	@Inject(method = "panicNearbyAnimals", at = @At("HEAD"), cancellable = true)
	private void noPanickingNearbyAnimals(DamageSource damageSource, CallbackInfo ci) {
		ci.cancel();
	}
}
