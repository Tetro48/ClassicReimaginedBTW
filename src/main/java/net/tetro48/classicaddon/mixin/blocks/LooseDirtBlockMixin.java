package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.LooseDirtBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LooseDirtBlock.class)
public abstract class LooseDirtBlockMixin extends Block {

	protected LooseDirtBlockMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/LooseDirtBlock;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
	public void changeDirtDropOnBadBreak(LooseDirtBlock instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
		this.dropItemsIndividually(world, i, j, k, instance.blockID, 1, iMetadata, fChanceOfDrop);
	}
}
