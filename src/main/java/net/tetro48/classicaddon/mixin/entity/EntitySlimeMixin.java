package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySlime.class)
public abstract class EntitySlimeMixin extends EntityLiving {
	public EntitySlimeMixin(World par1World) {
		super(par1World);
	}

	@Shadow protected abstract boolean canDamagePlayer();

	@Shadow public abstract int getSlimeSize();

	@Shadow protected abstract int getAttackStrength();

	@Inject(method = "canDamagePlayer", at = @At("RETURN"), cancellable = true)
	private void doNotDamageAsSmallSlime(CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) {
			cir.setReturnValue(getSlimeSize() > 1);
		}
	}
	@Inject(method = "onCollideWithPlayer", at = @At("HEAD"), cancellable = true)
	private void vanillaifySlimes(EntityPlayer player, CallbackInfo ci) {
		if (this.canDamagePlayer()) {
			int var2 = this.getSlimeSize();
			if (this.canEntityBeSeen(player) && this.getDistanceSqToEntity(player) < 0.6d * var2 * 0.6d * var2 && player.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength())) {
				this.playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}
		}
		ci.cancel();
	}
}
