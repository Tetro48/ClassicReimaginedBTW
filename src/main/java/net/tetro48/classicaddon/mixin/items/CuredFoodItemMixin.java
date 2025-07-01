package net.tetro48.classicaddon.mixin.items;

import btw.item.items.CuredFoodItem;
import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CuredFoodItem.class)
public abstract class CuredFoodItemMixin extends Item {
	public CuredFoodItemMixin(int par1) {
		super(par1);
	}

	@Inject(method = "<init>", at = @At("TAIL"), remap = false)
	public void changeStackSize(CallbackInfo ci) {
		this.maxStackSize = 64;
	}
}
