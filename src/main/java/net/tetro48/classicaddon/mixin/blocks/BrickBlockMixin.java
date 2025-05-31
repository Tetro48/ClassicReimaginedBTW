package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.BrickBlock;
import net.minecraft.src.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrickBlock.class)
public class BrickBlockMixin {
	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDropType(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.brick.blockID);
	}
}
