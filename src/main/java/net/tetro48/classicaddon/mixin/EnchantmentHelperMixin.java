package net.tetro48.classicaddon.mixin;

import net.minecraft.src.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
	@ModifyConstant(method = "calcItemStackEnchantability", constant = @Constant(intValue = 15))
	private static int increaseMaxEnchantmentLevel(int oldLevel) {
		return 30;
	}
	@ModifyConstant(method = "calcItemStackEnchantability", constant = @Constant(intValue = 1, ordinal = 1))
	private static int decreaseBookshelvesNeededToIncreaseLevel(int bitShiftValue) {
		return 0;
	}
}
