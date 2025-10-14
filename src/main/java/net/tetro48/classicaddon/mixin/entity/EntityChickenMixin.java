package net.tetro48.classicaddon.mixin.entity;

import btw.world.util.WorldUtils;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
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
	public long accelerationTicks = 0;

	public EntityChickenMixin(World par1World) {
		super(par1World);
	}
	@Inject(method = "writeEntityToNBT", at = @At("TAIL"))
	private void writeNewNBT(NBTTagCompound tag, CallbackInfo ci) {
		tag.setBoolean("tcDespawnFlag", despawnFlag);
		tag.setLong("crAccelerationTicks", accelerationTicks);
	}
	@Inject(method = "readEntityFromNBT", at = @At("TAIL"))
	private void readNewNBT(NBTTagCompound tag, CallbackInfo ci) {
		if (tag.hasKey("tcDespawnFlag")) {
			despawnFlag = tag.getBoolean("tcDespawnFlag");
		}
		if (tag.hasKey("crAccelerationTicks")) {
			accelerationTicks = tag.getLong("crAccelerationTicks");
		}
		else {
			accelerationTicks = 0;
		}
	}

	@Inject(method = "isReadyToEatBreedingItem", at = @At("HEAD"), cancellable = true)
	private void doNotEatIfAccelerationActive(CallbackInfoReturnable<Boolean> cir) {
		if (accelerationTicks > 600) {
			cir.setReturnValue(false);
		}
		else if (this.isFullyFed() && this.getGrowingAge() == 0) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "onEatBreedingItem", at = @At("HEAD"), cancellable = true)
	private void accelerateEggLaying(CallbackInfo ci) {
		this.accelerationTicks += 23700L + this.rand.nextInt(600);
		this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), this.rand.nextFloat() * 0.2F + 1.5F);
		ci.cancel();
	}
	@Inject(method = "updateHungerState", at = @At("HEAD"), cancellable = true)
	private void manageEggLayingState(CallbackInfo ci) {
		if (despawnFlag) {
			ci.cancel();
			return;
		}
		if (this.timeToLayEgg == 0) {
			long currentTime = WorldUtils.getOverworldTimeServerOnly();
			/// The bound can not go any higher than 48000L (2 days)
			this.timeToLayEgg = currentTime + this.rand.nextLong(12000L, 48000L);
		}
		if (this.accelerationTicks > 0) {
			this.accelerationTicks--;
			//effectively x8 acceleration
			this.timeToLayEgg -= 7;
		}
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
