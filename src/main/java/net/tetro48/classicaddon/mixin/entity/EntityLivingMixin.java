package net.tetro48.classicaddon.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.Item;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EntityLiving.class)
public abstract class EntityLivingMixin extends EntityLivingBase {
	@Mutable
	@Shadow @Final private static float[] enchantmentProbability;

	@Mutable
	@Shadow @Final private static float[] armorEnchantmentProbability;

	@Mutable
	@Shadow @Final private static float[] armorProbability;

	public EntityLivingMixin(World par1World) {
		super(par1World);
	}

	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void increaseOddsForHigherDifficulties(CallbackInfo ci) {
		enchantmentProbability = new float[]{0.25F, 0.25F, 0.25F, 0.25F};
		armorEnchantmentProbability = new float[]{0.25F, 0.25F, 0.25F, 0.5F};
		armorProbability = new float[]{0.15F, 0.15F, 0.15F, 0.15F};
	}

	@WrapOperation(method = "entityLivingAddRandomArmor", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F", ordinal = 0))
	private float makeArmorSpawnRateBasedOnLocalTension(Random instance, Operation<Float> original) {
		return original.call(instance) / this.worldObj.getLocationTensionFactor(posX, posY, posZ);
	}
	@WrapOperation(method = "enchantEquipment", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
	private float makeEnchantmentBasedOnLocalTension(Random instance, Operation<Float> original) {
		return original.call(instance) / this.worldObj.getLocationTensionFactor(posX, posY, posZ);
	}

	@ModifyVariable(method = "entityLivingAddRandomArmor", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F", ordinal = 1))
	private float lowerArmorSpawnOddsOnLowerDifficulty(float value) {
		if (this.worldObj.difficultySetting < 3) return 0.25f;
		return value;
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
