package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityFishHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFishHook.class)
public abstract class EntityFishHookMixin {
	@Shadow private int ticksCatchable;

	@ModifyArg(method = "onUpdate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 1))
	private int reduceRandomRange(int bound) {
		return bound - 10;
	}
	@Inject(method = "onUpdate", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/src/EntityFishHook;playSound(Ljava/lang/String;FF)V"))
	private void moreLenientCatches(CallbackInfo ci) {
		this.ticksCatchable += 10;
	}
	@ModifyArg(method = "checkForBite", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
	private int lowerTimeToBite(int bound) {
		return bound / 2;
	}
}
