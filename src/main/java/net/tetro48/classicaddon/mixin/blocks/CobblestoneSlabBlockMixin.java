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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(CobblestoneSlabBlock.class)
public abstract class CobblestoneSlabBlockMixin extends Block {

	@Shadow public abstract int getStrata(int iMetadata);

	protected CobblestoneSlabBlockMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void makeItChiselable(int iBlockID, boolean bDoubleSlab, CallbackInfo ci) {
		this.setChiselsCanHarvest();
		this.setChiselsEffectiveOn();
	}
	@Override
	public void onBlockDestroyedWithImproperTool(World world, EntityPlayer player, int i, int j, int k, int iMetadata) {}
	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void makeItDropItself(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(BTWBlocks.cobblestoneSlab.blockID);
	}
	@Inject(method = "damageDropped", at = @At("RETURN"), cancellable = true)
	public void dropCorrectStrataCobble(int metadata, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(this.getStrata(metadata));
	}
	public int damageDropped(int metadata) {
		return this.getStrata(metadata) & 3;
	}
}
