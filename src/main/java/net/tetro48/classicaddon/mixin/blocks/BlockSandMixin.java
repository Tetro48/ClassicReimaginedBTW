package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.LooseDirtBlock;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockSand.class)
public abstract class BlockSandMixin extends Block {

	protected BlockSandMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/BlockSand;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
	public void changeDropOnBadBreak(BlockSand instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
		this.dropItemsIndividually(world, i, j, k, instance.blockID, 1, iMetadata, fChanceOfDrop);
	}

	@Override
	public float getMovementModifier(World world, int i, int j, int k) {
		return 1F;
	}
}
