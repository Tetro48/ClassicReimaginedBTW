package net.tetro48.classicaddon.mixin;

import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	@Inject(method = "calcItemStackEnchantability", at = @At("HEAD"), cancellable = true)
	private static void overrideCalcItemStackEnchantability(Random rand, int iTableSlotNum, int iNumBookShelves, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		Item item = stack.getItem();
		int iItemEnchantability = item.getItemEnchantability();
		if (iItemEnchantability <= 0) {
			cir.setReturnValue(0);
		} else {
			int iEnchantmentLevel = 1;
			if (iTableSlotNum != 0) {
				int iMaxEnchantmentLevel = iNumBookShelves * 2;
				iMaxEnchantmentLevel = MathHelper.clamp_int(iMaxEnchantmentLevel, 1, 30);

				if (iTableSlotNum == 1) {
					if (iMaxEnchantmentLevel > 1) {
						iEnchantmentLevel = 2;
						if (iMaxEnchantmentLevel > 3) {
							iEnchantmentLevel += rand.nextInt(iMaxEnchantmentLevel - 2);
						}
					}
				} else {
					iEnchantmentLevel = iMaxEnchantmentLevel;
				}
			}

			cir.setReturnValue(iEnchantmentLevel);
		}
	}
}
