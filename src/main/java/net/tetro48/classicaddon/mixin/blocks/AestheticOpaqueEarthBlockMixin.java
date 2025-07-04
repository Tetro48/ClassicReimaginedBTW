package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.AestheticOpaqueEarthBlock;
import btw.item.BTWItems;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AestheticOpaqueEarthBlock.class)
public abstract class AestheticOpaqueEarthBlockMixin extends Block {
	protected AestheticOpaqueEarthBlockMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "dropAsPiles", at = @At("HEAD"), cancellable = true)
	private void dropAsBlocks(World world, int i, int j, int k, int iMetadata, float fChanceOfPileDrop, CallbackInfo ci) {

		Block blockToDrop = Block.dirt;
		int blockDropMetadata = 0;
		if (iMetadata == 6 || iMetadata == 7) {
			blockToDrop = BTWBlocks.aestheticEarth;
			blockDropMetadata = iMetadata;
		}

		if (world.rand.nextFloat() <= fChanceOfPileDrop) {
			ItemStack tempStack = new ItemStack(blockToDrop, 1, blockDropMetadata);
			this.dropBlockAsItem_do(world, i, j, k, tempStack);
		}
		ci.cancel();
	}
}
