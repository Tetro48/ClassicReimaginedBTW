package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.BlockStep;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStep.class)
public abstract class BlockStepMixin {
	@Inject(method = "getHarvestToolLevel", at = @At("RETURN"), cancellable = true)
	public void makeItMineableByAnyPickaxe(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
}
