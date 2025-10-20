package net.tetro48.classicaddon.mixin.items;

import btw.item.items.BucketItemMilk;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItemMilk.class)
public abstract class BucketItemMilkMixin extends Item {
	public BucketItemMilkMixin(int par1) {
		super(par1);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void increaseStackLimit(int iItemID, CallbackInfo ci) {
		this.maxStackSize = 16;
	}
	@Inject(method = "onEaten", at = @At("HEAD"))
	private void onDrink(ItemStack itemStack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
		if (itemStack.stackSize > 1) {
			ItemStack bucket = new ItemStack(Item.bucketEmpty);
			if (!player.inventory.addItemStackToInventory(bucket)) {
				player.dropPlayerItem(bucket);
			}
		}
	}
}
