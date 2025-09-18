package net.tetro48.classicaddon.mixin.blocks;

import btw.community.classicaddon.ClassicAddon;
import btw.item.BTWItems;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockTallGrass.class)
public abstract class BlockTallGrassMixin extends Block {
	protected BlockTallGrassMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "dropBlockAsItemWithChance", at = @At("TAIL"))
	private void dropHempSeedsIfEnabled(World world, int x, int y, int z, int metadata, float chance, int fortuneModifier, CallbackInfo ci) {
		if (!world.isRemote && ClassicAddon.hempSeedDropFromTallGrass && world.getDifficulty().canGrassDropSeeds() && world.rand.nextInt(100) == 0) {
			this.dropBlockAsItem_do(world, x, y, z, new ItemStack(BTWItems.hempSeeds));
		}
	}
}
