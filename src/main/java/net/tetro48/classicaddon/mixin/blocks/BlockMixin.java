package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public abstract class BlockMixin {
	@Redirect(method = "<clinit>", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/src/BlockFurnace;hideFromEMI()Lnet/minecraft/src/Block;"))
	private static Block notHideFurnaceFromEMI(BlockFurnace instance) {
		return instance;
	}
}
