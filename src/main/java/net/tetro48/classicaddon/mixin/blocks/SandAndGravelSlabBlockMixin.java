package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.FallingSlabBlock;
import btw.block.blocks.SandAndGravelSlabBlock;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SandAndGravelSlabBlock.class)
public abstract class SandAndGravelSlabBlockMixin extends FallingSlabBlock {

	@Shadow public abstract int getSubtype(IBlockAccess blockAccess, int i, int j, int k);

	protected SandAndGravelSlabBlockMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/SandAndGravelSlabBlock;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
	public void changeDropOnBadBreak(SandAndGravelSlabBlock instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
		this.dropItemsIndividually(world, i, j, k, instance.blockID, 1, iMetadata, fChanceOfDrop);
	}

	@Override
	public float getMovementModifier(World world, int i, int j, int k) {
		float fModifier = 1.0F;
		int iSubtype = this.getSubtype(world, i, j, k);
		if (iSubtype == 0) {
			fModifier = 1.2F;
		}

		return fModifier;
	}
}
