package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.FallingSlabBlock;
import btw.block.blocks.SandAndGravelSlabBlock;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SandAndGravelSlabBlock.class)
public abstract class SandAndGravelSlabBlockMixin extends FallingSlabBlock {

    protected SandAndGravelSlabBlockMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    @Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/SandAndGravelSlabBlock;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
    public void changeDropOnBadBreak(SandAndGravelSlabBlock instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
        this.dropItemsIndividually(world, i, j, k, instance.blockID, 1, iMetadata, fChanceOfDrop);
    }
}
