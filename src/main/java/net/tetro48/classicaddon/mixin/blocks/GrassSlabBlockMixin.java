package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.GrassSlabBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrassSlabBlock.class)
public abstract class GrassSlabBlockMixin extends Block {

    protected GrassSlabBlockMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    @Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
    public void changeDirtDrop(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(BTWBlocks.dirtSlab.blockID);
    }
    @Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/GrassSlabBlock;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
    public void changeDirtDropOnBadBreak(GrassSlabBlock instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
        this.dropItemsIndividually(world, i, j, k, BTWBlocks.dirtSlab.blockID, 1, iMetadata, fChanceOfDrop);
    }
}
