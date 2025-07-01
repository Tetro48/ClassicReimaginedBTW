package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.CobblestoneStairsBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(CobblestoneStairsBlock.class)
public abstract class CobblestoneStairsBlockMixin extends Block {
	@Shadow private int strata;

	protected CobblestoneStairsBlockMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Redirect(method = "onBlockDestroyedWithImproperTool", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/CobblestoneStairsBlock;dropBlockAsItem(Lnet/minecraft/src/World;IIIII)V"))
	public void noFistingCobble(CobblestoneStairsBlock instance, World world, int i, int j, int k, int metadata, int quantityBonus) {}
	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void makeItDropItself(CallbackInfoReturnable<Integer> cir) {
		int blockID = Block.stairsCobblestone.blockID;
		if (this.strata != 0) {
			if (this.strata == 1) {
				blockID = BTWBlocks.midStrataCobblestoneStairs.blockID;
			} else {
				blockID = BTWBlocks.deepStrataCobblestoneStairs.blockID;
			}
		}
		cir.setReturnValue(blockID);
	}

}
