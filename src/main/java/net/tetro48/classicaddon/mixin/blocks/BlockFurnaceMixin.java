package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFurnace.class)
public abstract class BlockFurnaceMixin {

	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDropType(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.furnaceIdle.blockID);
	}

	@Inject(method = "quantityDropped", at = @At("RETURN"), cancellable = true)
	public void changeDropQuantity(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(1);
	}
}
