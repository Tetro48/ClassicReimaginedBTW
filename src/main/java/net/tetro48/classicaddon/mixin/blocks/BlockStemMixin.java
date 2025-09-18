package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.BlockStem;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStem.class)
public abstract class BlockStemMixin {
	@Inject(method = "canGrowFruitAt", at = @At("HEAD"), cancellable = true)
	private void checkIfBlockNotStem(World world, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
		int iBlockID = world.getBlockId(i, j, k);
		Block block = Block.blocksList[iBlockID];
		if (block instanceof BlockStem) {
			cir.setReturnValue(false);
		}
	}
}
