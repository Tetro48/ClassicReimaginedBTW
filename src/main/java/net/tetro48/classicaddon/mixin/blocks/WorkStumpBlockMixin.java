package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.WorkStumpBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorkStumpBlock.class)
public abstract class WorkStumpBlockMixin extends Block {
	protected WorkStumpBlockMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "getBlockHardness", at = @At("RETURN"), cancellable = true)
	public void undoStumpHardness(World world, int i, int j, int k, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(super.getBlockHardness(world, i, j, k));
	}
}
