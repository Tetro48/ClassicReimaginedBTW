package net.tetro48.classicaddon.mixin.items;

import btw.community.classicaddon.ClassicAddon;
import btw.item.items.BucketItemEmpty;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItemEmpty.class)
public abstract class BucketItemEmptyMixin extends Item {
	public BucketItemEmptyMixin(int par1) {
		super(par1);
	}

	@Inject(method = "onItemRightClick", at = @At("HEAD"), cancellable = true)
	private void onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
		if (ClassicAddon.vanillaifyBuckets) {
			MovingObjectPosition var5 = this.getMovingObjectPositionFromPlayer(world, player, true);
			if (var5 == null) {
				cir.setReturnValue(itemStack);
			} else {
				if (var5.typeOfHit == EnumMovingObjectType.TILE) {
					int var6 = var5.blockX;
					int var7 = var5.blockY;
					int var8 = var5.blockZ;
					if (!world.canMineBlock(player, var6, var7, var8)) {
						cir.setReturnValue(itemStack);
						return;
					}
					if (!player.canPlayerEdit(var6, var7, var8, var5.sideHit, itemStack)) {
						cir.setReturnValue(itemStack);
						return;
					}

					if (world.getBlockMaterial(var6, var7, var8) == Material.water && world.getBlockMetadata(var6, var7, var8) == 0) {
						world.setBlockToAir(var6, var7, var8);
						if (player.capabilities.isCreativeMode) {
							cir.setReturnValue(itemStack);
							return;
						}

						if (--itemStack.stackSize <= 0) {
							cir.setReturnValue(new ItemStack(Item.bucketWater));
							return;
						}

						if (!player.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater))) {
							player.dropPlayerItem(new ItemStack(Item.bucketWater.itemID, 1, 0));
						}
					}

					if (world.getBlockMaterial(var6, var7, var8) == Material.lava && world.getBlockMetadata(var6, var7, var8) == 0) {
						world.setBlockToAir(var6, var7, var8);
						if (player.capabilities.isCreativeMode) {
							cir.setReturnValue(itemStack);
							return;
						}

						if (--itemStack.stackSize <= 0) {
							cir.setReturnValue(new ItemStack(Item.bucketLava));
							return;
						}

						if (!player.inventory.addItemStackToInventory(new ItemStack(Item.bucketLava))) {
							player.dropPlayerItem(new ItemStack(Item.bucketLava.itemID, 1, 0));
						}
					}
				}

				cir.setReturnValue(itemStack);
			}
		}
	}
}
