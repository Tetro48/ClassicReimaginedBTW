package net.tetro48.classicaddon.mixin.blocks;

import api.block.blocks.CropsBlock;
import btw.block.blocks.CarrotBlockFlowers;
import btw.community.classicaddon.ClassicAddon;
import btw.item.BTWItems;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(CarrotBlockFlowers.class)
public abstract class CarrotBlockFlowersMixin extends CropsBlock {

	protected CarrotBlockFlowersMixin(int iBlockID) {
		super(iBlockID);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chanceOfDrop, int fortuneModifier) {
		if (!world.isRemote)
		{
			if (isFullyGrown(metadata))
			{
				this.dropBlockAsItem_do(world, x, y, z, new ItemStack(BTWItems.carrotSeeds));
				int chances = 3 + fortuneModifier;

				for (int i = 0; i < chances; ++i)
				{
					if (world.rand.nextInt(15) <= (metadata & 0b0111))
					{
						this.dropBlockAsItem_do(world, x, y, z, new ItemStack(BTWItems.carrotSeeds));
					}
				}
				if (ClassicAddon.guaranteedSeedDrop) {
					this.dropBlockAsItem_do(world, x, y, z, new ItemStack(BTWItems.carrotSeeds));
				}
			}
			else {
				this.dropSeeds(world, x, y, z, metadata);
			}
		}
	}

	@Override
	protected int getSeedItemID() {
		return BTWItems.carrot.itemID;
	}

	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
		return this.isFullyGrown(iMetadata) ? this.getCropItemID() : this.getSeedItemID();
	}
}
