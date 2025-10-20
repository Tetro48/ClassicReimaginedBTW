package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EntityPigZombie.class)
public abstract class EntityPigZombieMixin extends EntityZombie {
	public EntityPigZombieMixin(World par1World) {
		super(par1World);
	}

	@Inject(method = "attackEntityFrom", at = @At("HEAD"))
	private void reduceDropChanceIfPlayerAttack(DamageSource source, float iDamage, CallbackInfoReturnable<Boolean> cir) {
		if (source.getSourceOfDamage() instanceof EntityPlayer) {
			this.equipmentDropChances[0] = 0.08f;
		}
		else {
			this.equipmentDropChances[0] = 2f/3f;
		}
	}

	@Redirect(method = "addRandomArmor", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
	private float guaranteeSword(Random instance) {
		return 0f;
	}
}
