package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.HandCrankBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HandCrankBlock.class)
public abstract class HandCrankBlockMixin {
	@ModifyArg(method = "onBlockActivated", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V"))
	private float undoCostMultiplier(float par1) {
		return 2;
	}
}
