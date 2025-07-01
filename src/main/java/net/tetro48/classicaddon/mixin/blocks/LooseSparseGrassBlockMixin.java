package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.LooseSparseGrassBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LooseSparseGrassBlock.class)
public abstract class LooseSparseGrassBlockMixin extends Block {
	protected LooseSparseGrassBlockMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDirtDrop(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.dirt.blockID);
	}
	@Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/LooseSparseGrassBlock;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
	public void changeDirtDropOnBadBreak(LooseSparseGrassBlock instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
		this.dropItemsIndividually(world, i, j, k, Block.dirt.blockID, 1, iMetadata, fChanceOfDrop);
	}
}
