package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.RoughStoneBlock;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RoughStoneBlock.class)
public class RoughStoneBlockMixin {
	@Inject(method = "getHarvestToolLevel", at = @At("RETURN"), cancellable = true)
	public void makeItMineableByAnyPickaxe(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
	@Inject(method = "getEfficientToolLevel", at = @At("RETURN"), cancellable = true)
	public void allPickaxesAreEfficientForAnyStrata(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/RoughStoneBlock;setHardness(F)Lnet/minecraft/src/Block;"))
	public float sameBreakingSpeed(float par1) {
		return 2.25f;
	}
}
