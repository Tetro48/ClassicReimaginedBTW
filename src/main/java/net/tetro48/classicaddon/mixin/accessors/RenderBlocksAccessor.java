package net.tetro48.classicaddon.mixin.accessors;

import net.minecraft.src.BlockHopper;
import net.minecraft.src.RenderBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderBlocks.class)
public interface RenderBlocksAccessor {
	@Invoker("renderBlockHopper")
	boolean callRenderBlockHopper(BlockHopper par1BlockHopper, int par2, int par3, int par4);
}
