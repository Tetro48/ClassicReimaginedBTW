package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.FullBlock;
import net.minecraft.src.Block;
import net.minecraft.src.BlockDirt;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockDirt.class)
public class BlockDirtMixin extends FullBlock {
    protected BlockDirtMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    @Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
    public void changeDirtDrop(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Block.dirt.blockID);
    }
    @Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/BlockDirt;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
    public void changeDirtDropOnBadBreak(BlockDirt instance, World world, int i, int j, int k, int id, int amount, int iMetadata, float fChanceOfDrop) {
        this.dropBlockAsItem(world, i, j, k, instance.blockID, 1);
    }
    @Inject(method = "onNeighborDirtDugWithImproperTool", at = @At("HEAD"), cancellable = true)
    protected void noConvertDirt(World world, int i, int j, int k, int iToFacing, CallbackInfo ci) {
        ci.cancel();
    }
}
