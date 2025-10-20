package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLiving.class)
public abstract class EntityLivingMixin {
	@Mutable
	@Shadow @Final private static float[] enchantmentProbability;

	@Mutable
	@Shadow @Final private static float[] armorEnchantmentProbability;

	@Mutable
	@Shadow @Final private static float[] armorProbability;

	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void increaseOddsForHigherDifficulties(CallbackInfo ci) {
		enchantmentProbability = new float[]{0.05F, 0.05F, 0.05F, 0.1F};
		armorEnchantmentProbability = new float[]{0.05F, 0.05F, 0.05F, 0.2F};
		armorProbability = new float[]{0.0025F, 0.0025F, 0.015F, 0.075F};
	}

	@Inject(method = "getArmorItemForSlot", at = @At("HEAD"), cancellable = true)
	public void getArmorItemForSlot(int par0, int par1, CallbackInfoReturnable<Item> cir) {
		cir.setReturnValue(switch (par0) {
			case 1 -> {
				if (par1 == 4) {
					yield Item.bootsDiamond;
				}
				if (par1 == 3) {
					yield Item.bootsChain;
				}

				yield Item.bootsIron;
			}
			case 2 -> {
				if (par1 == 4) {
					yield Item.legsDiamond;
				}
				if (par1 == 3) {
					yield Item.legsChain;
				}

				yield Item.legsIron;
			}
			case 3 -> {
				if (par1 == 4) {
					yield Item.plateDiamond;
				}
				if (par1 == 3) {
					yield Item.plateChain;
				}

				yield Item.plateIron;
			}
			case 4 -> {
				if (par1 == 4) {
					yield Item.helmetDiamond;
				}
				if (par1 == 3) {
					yield Item.helmetChain;
				}

				yield Item.helmetIron;
			}
			default -> null;
		});
	}
}
