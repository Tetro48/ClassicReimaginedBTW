package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.BrickStairsBlock;
import btw.block.blocks.CobblestoneBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrickStairsBlock.class)
public abstract class BrickStairsBlockMixin extends Block {
	protected BrickStairsBlockMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}
	@Redirect(method = "onBlockDestroyedWithImproperTool", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/BrickStairsBlock;dropBlockAsItem(Lnet/minecraft/src/World;IIIII)V"))
	public void noFistingMortared(BrickStairsBlock instance, World world, int i, int j, int k, int metadata, int quantityBonus) {}
	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void makeItDropItself(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.stairsBrick.blockID);
	}
}
