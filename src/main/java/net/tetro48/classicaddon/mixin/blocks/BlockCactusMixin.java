package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCactus;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockCactus.class)
public abstract class BlockCactusMixin extends Block {
	protected BlockCactusMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "canPlaceBlockAt", at = @At("HEAD"), cancellable = true)
	private void canPlaceCactusAtSand(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {

		if (super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z)) {
			int blockBelowID = world.getBlockId(x, y - 1, z);
			if (blockBelowID == Block.sand.blockID) cir.setReturnValue(true);
		}
	}
}
