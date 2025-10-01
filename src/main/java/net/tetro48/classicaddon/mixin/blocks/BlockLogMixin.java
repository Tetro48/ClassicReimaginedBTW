package net.tetro48.classicaddon.mixin.blocks;

import btw.item.items.ChiselItem;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLog.class)
public abstract class BlockLogMixin extends BlockRotatedPillar {
	@Shadow public abstract boolean isWorkStumpItemConversionTool(ItemStack stack, World world, int i, int j, int k);

	@Shadow public abstract boolean getIsStump(IBlockAccess blockAccess, int i, int j, int k);

	protected BlockLogMixin(int i, Material material) {
		super(i, material);
	}

	@Inject(method = "getIsProblemToRemove", at = @At("RETURN"), cancellable = true)
	public void makeStumpMineable(ItemStack toolStack, IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
	@Inject(method = "getBlockHardness", at = @At("RETURN"), cancellable = true)
	public void undoStumpHardness(World world, int i, int j, int k, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(super.getBlockHardness(world, i, j, k));
	}
}
