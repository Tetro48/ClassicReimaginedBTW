package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.BlockSandStone;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockSandStone.class)
public abstract class BlockSandStoneMixin {
	@Inject(method = "getHarvestToolLevel", at = @At("RETURN"), cancellable = true)
	public void makeItMineableByAnyPickaxe(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
}
