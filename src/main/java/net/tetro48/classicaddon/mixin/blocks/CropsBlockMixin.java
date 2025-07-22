package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.CropsBlock;
import btw.block.blocks.PlantsBlock;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CropsBlock.class)
public abstract class CropsBlockMixin extends PlantsBlock {
	@Shadow protected abstract boolean isFullyGrown(int iMetadata);

	@Shadow public abstract void dropSeeds(World world, int i, int j, int k, int iMetadata);

	@Shadow protected abstract boolean onlyDropWhenFullyGrown();

	protected CropsBlockMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Inject(method = "dropBlockAsItemWithChance", at = @At("HEAD"), cancellable = true)
	public void vanillaifyCropRates(World world, int x, int y, int z, int metadata, float chanceOfDrop, int fortuneModifier, CallbackInfo ci) {
		if (!world.isRemote)
		{
			if (!this.onlyDropWhenFullyGrown() || this.isFullyGrown(metadata)) {
				super.dropBlockAsItemWithChance(world, x, y, z, metadata, chanceOfDrop, 0);
			}
			if (isFullyGrown(metadata))
			{
				int chances = 3 + fortuneModifier;

				for (int i = 0; i < chances; ++i)
				{
					if (world.rand.nextInt(15) <= (metadata & 0b0111))
					{
						this.dropSeeds(world, x, y, z, metadata);
					}
				}
			}
			else {
				this.dropSeeds(world, x, y, z, metadata);
			}
		}
		ci.cancel();
	}
}
