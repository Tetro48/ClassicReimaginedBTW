package net.tetro48.classicaddon.mixin.blocks;

import api.block.blocks.StairsBlock;
import btw.block.BTWBlocks;
import btw.block.blocks.StoneBrickStairsBlock;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StoneBrickStairsBlock.class)
public abstract class StoneBrickStairsBlockMixin extends StairsBlock {

	@Shadow private int strata;

	public StoneBrickStairsBlockMixin(int iBlockID, Block referenceBlock, int iReferenceBlockMetadata) {
		super(iBlockID, referenceBlock, iReferenceBlockMetadata);
	}

	@Override
	public void onBlockDestroyedWithImproperTool(World world, EntityPlayer player, int i, int j, int k, int iMetadata) {}

	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void makeItDropItself(CallbackInfoReturnable<Integer> cir) {
		int blockID = Block.stairsStoneBrick.blockID;
		if (this.strata != 0) {
			if (this.strata == 1) {
				blockID = BTWBlocks.midStrataStoneBrickStairs.blockID;
			} else {
				blockID = BTWBlocks.deepStrataStoneBrickStairs.blockID;
			}
		}
		cir.setReturnValue(blockID);
	}
}
