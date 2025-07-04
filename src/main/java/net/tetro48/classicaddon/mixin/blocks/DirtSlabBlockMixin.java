package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.DirtSlabBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(DirtSlabBlock.class)
public abstract class DirtSlabBlockMixin extends Block {

	@Shadow public abstract int getSubtype(int iMetadata);

	protected DirtSlabBlockMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDirtDrop(int iMetadata, Random random, int iFortuneModifier, CallbackInfoReturnable<Integer> cir) {
		if (cir.getReturnValue() == BTWBlocks.looseDirtSlab.blockID) cir.setReturnValue(BTWBlocks.dirtSlab.blockID);
	}
	@Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/DirtSlabBlock;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
	public void changeDirtDropOnBadBreak(DirtSlabBlock instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
		if (this.getSubtype(iMetadata) == 3) {
			this.dropItemsIndividually(world, i, j, k, BTWBlocks.dirtSlab.blockID, 1, 3, fChanceOfDrop);
		}
		else {
			this.dropItemsIndividually(world, i, j, k, BTWBlocks.dirtSlab.blockID, 1, 0, fChanceOfDrop);
		}
	}
	@Inject(method = "onNeighborDirtDugWithImproperTool", at = @At("HEAD"), cancellable = true)
	protected void noConvertDirt(World world, int i, int j, int k, int iToFacing, CallbackInfo ci) {
		ci.cancel();
	}
}
