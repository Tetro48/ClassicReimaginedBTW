package net.tetro48.classicaddon.mixin.items;

import btw.item.items.FoodItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FoodItem.class)
public abstract class FoodItemMixin {
	@ModifyArg(method = "setStandardFoodPoisoningEffect", index = 1, at = @At(value = "INVOKE", target = "Lbtw/item/items/FoodItem;setPotionEffect(IIIF)Lnet/minecraft/src/ItemFood;"))
	private int lowerFoodPoisoningLength(int par1) {
		return 30;
	}
}
