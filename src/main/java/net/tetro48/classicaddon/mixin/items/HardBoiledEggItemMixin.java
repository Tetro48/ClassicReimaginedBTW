package net.tetro48.classicaddon.mixin.items;

import btw.item.items.HardBoiledEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HardBoiledEggItem.class)
public abstract class HardBoiledEggItemMixin {
	@ModifyArg(method = "<init>", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeFoodValue(int iItemID) {
		return 4;
	}
	@ModifyArg(method = "<init>", index = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changeSaturationValue(float par3) {
		return 0.8f;
	}
}
