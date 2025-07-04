package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.FarmlandBlock;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin {
	@Redirect(method = "onNeighborBlockChange", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/FarmlandBlock;canFallIntoBlockAtPos(Lnet/minecraft/src/World;III)Z"))
	private boolean doNotConvertFarmlandToDirt(FarmlandBlock instance, World world, int i, int j, int k) {
		return false;
	}
	@ModifyArg(method = "onNeighborBlockChange", index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;setBlockWithNotify(IIII)Z"))
	private int convertToDirtOnObstruction(int i) {
		return Block.dirt.blockID;
	}
}
