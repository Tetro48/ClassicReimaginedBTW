package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.FarmlandBlockBase;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FarmlandBlockBase.class)
public interface FarmlandBlockBaseInvoker {
	@Invoker("hasIrrigatingBlocks")
	boolean getHasIrrigatingBlocks(World world, int i, int j, int k);

	@Invoker("isHydrated")
	boolean getIsHydrated(int i);
}
