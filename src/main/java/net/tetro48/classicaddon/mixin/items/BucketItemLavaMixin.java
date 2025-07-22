package net.tetro48.classicaddon.mixin.items;

import btw.community.classicaddon.ClassicAddon;
import btw.item.items.BucketItemLava;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BucketItemLava.class)
public abstract class BucketItemLavaMixin extends Item {
	public BucketItemLavaMixin(int par1) {
		super(par1);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void lavaBucketsMustNotStack(int iItemID, CallbackInfo ci) {
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (ClassicAddon.vanillaifyBuckets) {
			MovingObjectPosition var5 = this.getMovingObjectPositionFromPlayer(world, player, true);
			if (var5 == null) {
				return itemStack;
			} else {
				if (var5.typeOfHit == EnumMovingObjectType.TILE) {
					int var6 = var5.blockX;
					int var7 = var5.blockY;
					int var8 = var5.blockZ;
					if (!world.canMineBlock(player, var6, var7, var8)) {
						return itemStack;
					}

					if (var5.sideHit == 0) {
						--var7;
					}

					if (var5.sideHit == 1) {
						++var7;
					}

					if (var5.sideHit == 2) {
						--var8;
					}

					if (var5.sideHit == 3) {
						++var8;
					}

					if (var5.sideHit == 4) {
						--var6;
					}

					if (var5.sideHit == 5) {
						++var6;
					}

					if (!player.canPlayerEdit(var6, var7, var8, var5.sideHit, itemStack)) {
						return itemStack;
					}

					if (tryPlaceContainedLiquid(world, var6, var7, var8) && !player.capabilities.isCreativeMode) {
						return new ItemStack(Item.bucketEmpty);
					}
				}
			}
		}
		return itemStack;
	}
	@Unique
	public boolean tryPlaceContainedLiquid(World world, int i, int j, int k) {
		Material var5 = world.getBlockMaterial(i, j, k);
		boolean var6 = !var5.isSolid();
		if (!world.isAirBlock(i, j, k) && !var6) {
			return false;
		} else {
			if (!world.isRemote && var6 && !var5.isLiquid()) {
				world.destroyBlock(i, j, k, true);
			}

			world.setBlockWithNotify(i, j, k, Block.lavaMoving.blockID);

			return true;
		}
	}
}
