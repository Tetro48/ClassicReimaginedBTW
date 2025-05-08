package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockGravel.class)
public abstract class BlockGravelMixin extends Block {

    protected BlockGravelMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    @Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/BlockGravel;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
    public void changeDropOnBadBreak(BlockGravel instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
        this.dropItemsIndividually(world, i, j, k, instance.blockID, 1, iMetadata, fChanceOfDrop);
    }
}
