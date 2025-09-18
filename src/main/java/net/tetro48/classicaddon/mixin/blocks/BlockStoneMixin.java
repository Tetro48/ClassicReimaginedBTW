package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.FullBlock;
import net.minecraft.src.BlockStone;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStone.class)
public abstract class BlockStoneMixin extends FullBlock {

	protected BlockStoneMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Inject(method = "getHarvestToolLevel", at = @At("RETURN"), cancellable = true)
	public void makeItMineableByAnyPickaxe(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
	@Inject(method = "getEfficientToolLevel", at = @At("RETURN"), cancellable = true)
	public void allPickaxesAreEfficientForAnyStrata(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
	@Inject(method = "getBlockHardness", at = @At("RETURN"), cancellable = true)
	public void lowerBlockHardness(World world, int i, int j, int k, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(cir.getReturnValue() / 1.5f);
	}
}
