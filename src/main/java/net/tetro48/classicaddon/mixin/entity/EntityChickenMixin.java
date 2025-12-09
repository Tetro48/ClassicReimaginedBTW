package net.tetro48.classicaddon.mixin.entity;

import api.world.WorldUtils;
import net.minecraft.src.*;
import net.tetro48.classicaddon.ForceDespawnableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityChicken.class)
public abstract class EntityChickenMixin extends EntityAnimal implements ForceDespawnableEntity {
	@Shadow protected long timeToLayEgg;
	@Unique
	public boolean despawnFlag = false;
	@Unique
	public long ticksUntilRNGEggs = 0;

	public EntityChickenMixin(World par1World) {
		super(par1World);
	}
	@Inject(method = "writeEntityToNBT", at = @At("TAIL"))
	private void writeNewNBT(NBTTagCompound tag, CallbackInfo ci) {
		tag.setBoolean("tcDespawnFlag", despawnFlag);
		tag.setLong("crAccelerationTicks", ticksUntilRNGEggs);
	}
	@Inject(method = "readEntityFromNBT", at = @At("TAIL"))
	private void readNewNBT(NBTTagCompound tag, CallbackInfo ci) {
		if (tag.hasKey("tcDespawnFlag")) {
			despawnFlag = tag.getBoolean("tcDespawnFlag");
		}
		if (tag.hasKey("crAccelerationTicks")) {
			ticksUntilRNGEggs = tag.getLong("crAccelerationTicks");
		}
		else {
			ticksUntilRNGEggs = 0;
		}
	}

	@Inject(method = "isReadyToEatBreedingItem", at = @At("HEAD"), cancellable = true)
	private void doNotEatIfAccelerationActive(CallbackInfoReturnable<Boolean> cir) {
		if (ticksUntilRNGEggs > 600) {
			cir.setReturnValue(false);
		}
		else if (this.isFullyFed() && this.getGrowingAge() == 0) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "onEatBreedingItem", at = @At("HEAD"), cancellable = true)
	private void accelerateEggLaying(CallbackInfo ci) {
		this.ticksUntilRNGEggs += 96000L + this.rand.nextInt(300);
		this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), this.rand.nextFloat() * 0.2F + 1.5F);
		ci.cancel();
	}
	@Inject(method = "updateHungerState", at = @At("HEAD"), cancellable = true)
	private void manageEggLayingState(CallbackInfo ci) {
		if (despawnFlag) {
			ci.cancel();
			return;
		}
		if (this.ticksUntilRNGEggs > 0) {
			this.ticksUntilRNGEggs--;
		}
		if (!this.isChild() && this.isFullyFed() && this.timeToLayEgg > 0L && WorldUtils.getOverworldTimeServerOnly() > this.timeToLayEgg) {
			if (ticksUntilRNGEggs > 0 || this.worldObj.rand.nextInt(3) == 0) {
				this.playSound("mob.chicken.plop", 1.0F, this.getSoundPitch());
				this.dropItem(Item.egg.itemID, 1);
			}
			this.timeToLayEgg = 0L;
		}
		if (this.timeToLayEgg == 0 && !this.isChild()) {
			long currentTime = WorldUtils.getOverworldTimeServerOnly();
			this.timeToLayEgg = ((currentTime + 12000L) / 24000L + 1L) * 24000L;
			this.timeToLayEgg += (-1450 + this.rand.nextInt(600));
		}
		ci.cancel();
	}

	@Inject(method = "validateTimeToLayEgg", at = @At("HEAD"), cancellable = true)
	private void skipValidation(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}

	@Override
	protected boolean canDespawn() {
		return despawnFlag;
	}

	@Override
	public void classicReimagined$setDespawnFlag(boolean despawnFlag) {
		this.despawnFlag = despawnFlag;
	}
}
