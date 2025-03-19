package net.tetro48.classicaddon.mixin.items;

import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFood.class)
public class ItemFoodMixin extends Item {
    public ItemFoodMixin(int par1) {
        super(par1);
    }

    @Inject(method = "<init>(IIFZ)V", at = @At("TAIL"))
    public void changeStackSize(CallbackInfo ci) {
        this.maxStackSize = 64;
    }
}
