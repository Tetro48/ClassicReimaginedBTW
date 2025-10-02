package net.tetro48.classicaddon.mixin.items;

import btw.item.items.FlintAndSteelItem;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(FlintAndSteelItem.class)
public abstract class FlintAndSteelItemMixin extends Item {
	public FlintAndSteelItemMixin(int par1) {
		super(par1);
	}

	@Inject(method = "checkChanceOfStart", at = @At("HEAD"), cancellable = true)
	protected void checkChanceOfStart(ItemStack stack, Random rand, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ) {

		int blockID = world.getBlockId(i, j, k);
		if (player.canPlayerEdit(i, j, k, iFacing, stack)) {
			Block clickedBlock = Block.blocksList[blockID];
			if (clickedBlock != null && clickedBlock.getCanBeSetOnFireDirectlyByItem(world, i, j, k)) {
				clickedBlock.setOnFireDirectly(world, i, j, k);
				if (!player.capabilities.isCreativeMode) {
					stack.damageItem(1, player);
				}
				return true;
			}
		}
		if (iFacing == 0) {
			--j;
		}

		if (iFacing == 1) {
			++j;
		}

		if (iFacing == 2) {
			--k;
		}

		if (iFacing == 3) {
			++k;
		}

		if (iFacing == 4) {
			--i;
		}

		if (iFacing == 5) {
			++i;
		}

		if (!player.canPlayerEdit(i, j, k, iFacing, stack)) {
			return false;
		} else {
			blockID = world.getBlockId(i, j, k);
			if (blockID == 0) {
				world.playSoundEffect(i + 0.5d, j + 0.5d, k + 0.5d, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlock(i, j, k, Block.fire.blockID);
			}

			stack.damageItem(1, player);
			return true;
		}
	}
}
