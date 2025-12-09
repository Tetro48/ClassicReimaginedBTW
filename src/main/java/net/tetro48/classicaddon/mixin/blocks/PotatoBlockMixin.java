package net.tetro48.classicaddon.mixin.blocks;

import api.block.blocks.DailyGrowthCropsBlock;
import btw.block.blocks.PotatoBlock;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotatoBlock.class)
public abstract class PotatoBlockMixin extends DailyGrowthCropsBlock {
	protected PotatoBlockMixin(int iBlockID) {
		super(iBlockID);
	}

	@Inject(method = "dropBlockAsItemWithChance", at = @At("HEAD"), cancellable = true)
	public void vanillafyPotatoRates(World world, int x, int y, int z, int metadata, float chanceOfDrop, int fortuneModifier, CallbackInfo ci) {
		super.dropBlockAsItemWithChance(world, x, y, z, metadata, chanceOfDrop, fortuneModifier);
		if (!world.isRemote)
		{
			if (this.isFullyGrown(metadata) && world.rand.nextInt(50) == 0)
			{
				this.dropBlockAsItem_do(world, x, y, z, new ItemStack(Item.poisonousPotato));
			}
		}
		ci.cancel();
	}
	@Inject(method = "getSeedItemID", at = @At("RETURN"), cancellable = true, remap = false)
	private void makeItReturnPotato(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Item.potato.itemID);
	}
}
