package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStoneBrick.class)
public abstract class BlockStoneBrickMixin extends Block {
	protected BlockStoneBrickMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}
	@Override
	public void onBlockDestroyedWithImproperTool(World world, EntityPlayer player, int i, int j, int k, int iMetadata) {}
	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDropType(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.stoneBrick.blockID);
	}
}
