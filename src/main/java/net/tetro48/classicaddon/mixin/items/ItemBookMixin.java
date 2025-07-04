package net.tetro48.classicaddon.mixin.items;

import net.minecraft.src.ItemBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBook.class)
public abstract class ItemBookMixin {
	@Inject(method = "getItemEnchantability", at = @At("RETURN"), cancellable = true)
	private void makeBooksEnchantable(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(1);
	}
}
