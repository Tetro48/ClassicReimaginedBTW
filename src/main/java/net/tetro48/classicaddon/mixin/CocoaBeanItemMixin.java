package net.tetro48.classicaddon.mixin;

import btw.item.items.CocoaBeanItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CocoaBeanItem.class)
public class CocoaBeanItemMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeFoodValue(int iItemID) {
        return 3;
    }
}
