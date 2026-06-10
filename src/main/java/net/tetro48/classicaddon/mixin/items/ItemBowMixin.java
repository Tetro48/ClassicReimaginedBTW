package net.tetro48.classicaddon.mixin.items;

import net.minecraft.src.ItemBow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBow.class)
public abstract class ItemBowMixin {
	@Inject(method = "getItemEnchantability", at = @At("RETURN"), cancellable = true)
	private void makeBowEnchantable(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(1);
	}
}
