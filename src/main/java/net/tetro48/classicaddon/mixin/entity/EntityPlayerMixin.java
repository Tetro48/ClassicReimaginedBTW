package net.tetro48.classicaddon.mixin.entity;

import btw.world.util.difficulty.DifficultyParam;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
	@Shadow public abstract World getEntityWorld();

	@ModifyArg(method = "addMovementStat", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V"))
	private float returnToVanillaValues(float par1) {
		return par1 / this.getEntityWorld().getDifficultyParameter(DifficultyParam.HungerIntensiveActionCostMultiplier.class);
	}
	@ModifyArg(method = "addExhaustionForJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V", ordinal = 0))
	private float makeSprintJumpsExpensiveAgain(float par1) {
		return 0.8f;
	}
	@ModifyArg(method = "addExhaustionForJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V", ordinal = 1))
	private float makeJumpsExpensiveAgain(float par1) {
		return 0.2f;
	}

}
