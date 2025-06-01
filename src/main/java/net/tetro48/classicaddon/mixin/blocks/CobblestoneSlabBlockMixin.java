package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.CobblestoneSlabBlock;
import btw.block.blocks.CobblestoneStairsBlock;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(CobblestoneSlabBlock.class)
public abstract class CobblestoneSlabBlockMixin extends Block {

    @Shadow public abstract int getStrata(int iMetadata);

    protected CobblestoneSlabBlockMixin(int par1, Material par2Material) {
        super(par1, par2Material);
    }

    @Override
    public void onBlockDestroyedWithImproperTool(World world, EntityPlayer player, int i, int j, int k, int iMetadata) {}
    @Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
    public void makeItDropItself(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(BTWBlocks.cobblestoneSlab.blockID);
    }
    public int damageDropped(int metadata) {
        return this.getStrata(metadata) & 3;
    }
    @Inject(method = "getHarvestToolLevel", at = @At("RETURN"), cancellable = true)
    public void makeItMineableByAnyPickaxe(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}
