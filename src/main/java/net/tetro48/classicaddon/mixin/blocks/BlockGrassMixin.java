package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockGrass.class)
public class BlockGrassMixin extends Block {
    protected BlockGrassMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    @Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
    public void changeDirtDrop(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Block.dirt.blockID);
    }
    @Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/BlockGrass;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
    public void changeDirtDropOnBadBreak(BlockGrass instance, World world, int i, int j, int k, int id, int amount, int iMetadata, float fChanceOfDrop) {
        this.dropBlockAsItem(world, i, j, k, Block.dirt.blockID, 1);
    }
    @Inject(method = "onNeighborDirtDugWithImproperTool", at = @At("HEAD"), cancellable = true)
    protected void noConvertDirt(World world, int i, int j, int k, int iToFacing, CallbackInfo ci) {
        ci.cancel();
    }
}
