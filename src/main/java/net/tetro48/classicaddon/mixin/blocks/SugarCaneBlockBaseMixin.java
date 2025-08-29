package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.SugarCaneBlockBase;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SugarCaneBlockBase.class)
public abstract class SugarCaneBlockBaseMixin extends Block {
	protected SugarCaneBlockBaseMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {}
}
