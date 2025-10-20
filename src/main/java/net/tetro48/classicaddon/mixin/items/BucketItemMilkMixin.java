package net.tetro48.classicaddon.mixin.items;

import btw.item.items.BucketItemMilk;
import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BucketItemMilk.class)
public abstract class BucketItemMilkMixin extends Item {
	public BucketItemMilkMixin(int par1) {
		super(par1);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void increaseStackLimit(int iItemID, CallbackInfo ci) {
		this.maxStackSize = 16;
	}
}
