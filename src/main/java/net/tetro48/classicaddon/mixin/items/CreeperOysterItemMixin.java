package net.tetro48.classicaddon.mixin.items;

import btw.item.items.CreeperOysterItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreeperOysterItem.class)
public abstract class CreeperOysterItemMixin {
	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
	private static float modifySaturationGain(float original) {
		return 0.25f;
	}
}
