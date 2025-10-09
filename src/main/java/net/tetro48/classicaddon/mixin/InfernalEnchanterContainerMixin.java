package net.tetro48.classicaddon.mixin;

import btw.inventory.container.InfernalEnchanterContainer;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InfernalEnchanterContainer.class)
public abstract class InfernalEnchanterContainerMixin {
	@ModifyArg(method = "computeCurrentEnchantmentLevels", index = 1, at = @At(value = "INVOKE", target = "Lbtw/inventory/container/InfernalEnchanterContainer;setCurrentEnchantingLevels(III)V"))
	private int doNotIncreaseCost(int origin) {
		return 1;
	}
	@Inject(method = "getMaximumNumberOfEnchantments", at = @At("HEAD"), cancellable = true)
	private void removeLimiter(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Integer.MAX_VALUE);
	}
}
