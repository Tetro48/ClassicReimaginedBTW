package net.tetro48.classicaddon.mixin.items;

import btw.item.items.CocoaBeanItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CocoaBeanItem.class)
public abstract class CocoaBeanItemMixin {
    @ModifyArg(method = "<init>", index = 1, at = @At(value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"))
    private static int changeFoodValue(int iItemID) {
        return 3;
    }
}
