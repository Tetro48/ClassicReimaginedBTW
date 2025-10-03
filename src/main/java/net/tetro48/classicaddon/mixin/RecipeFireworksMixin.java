package net.tetro48.classicaddon.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeFireworks.class)
public abstract class RecipeFireworksMixin {
	@Shadow private ItemStack field_92102_a;

	@Inject(method = "getCraftingResult", at = @At("RETURN"), cancellable = true)
	// this is a backport of the modern change
	private void increaseFireworksOutput(CallbackInfoReturnable<ItemStack> cir) {
		ItemStack itemStack = cir.getReturnValue();
		if (itemStack != null && itemStack.itemID == Item.firework.itemID) {
			itemStack.stackSize = 3;
		}
		cir.setReturnValue(itemStack);
	}
}
