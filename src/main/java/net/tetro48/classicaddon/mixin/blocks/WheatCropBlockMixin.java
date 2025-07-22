package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.CropsBlock;
import btw.block.blocks.WheatCropBlock;
import btw.item.BTWItems;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(WheatCropBlock.class)
public abstract class WheatCropBlockMixin extends CropsBlock {

	protected WheatCropBlockMixin(int iBlockID) {
		super(iBlockID);
	}

	@Override
	protected int getSeedItemID() {
		return BTWItems.wheatSeeds.itemID;
	}

	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
		return this.isFullyGrown(iMetadata) ? this.getCropItemID() : this.getSeedItemID();
	}
}
