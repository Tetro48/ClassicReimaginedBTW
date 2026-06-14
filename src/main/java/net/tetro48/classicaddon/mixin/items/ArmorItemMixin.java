package net.tetro48.classicaddon.mixin.items;

import api.item.items.ArmorItem;
import net.tetro48.classicaddon.mixin.accessors.ArmorItemAccessor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin {
	@Inject(method = "<init>(ILnet/minecraft/src/EnumArmorMaterial;IIID)V", at = @At("TAIL"))
	public void zeroArmorWeight(CallbackInfo ci) {
		((ArmorItemAccessor)this).setArmorWeight(0);
	}
}
