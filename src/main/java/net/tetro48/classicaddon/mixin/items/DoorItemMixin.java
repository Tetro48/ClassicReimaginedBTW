package net.tetro48.classicaddon.mixin.items;

import btw.item.items.DoorItem;
import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DoorItem.class)
public class DoorItemMixin extends Item {
    public DoorItemMixin(int par1) {
        super(par1);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void changeStackSize(CallbackInfo ci) {
        this.maxStackSize = 64;
    }
}
