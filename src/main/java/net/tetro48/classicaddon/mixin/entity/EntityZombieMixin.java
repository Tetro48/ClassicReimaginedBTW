package net.tetro48.classicaddon.mixin.entity;

import btw.item.BTWItems;
import net.minecraft.src.*;
import net.tetro48.classicaddon.ForceDespawnableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityZombie.class)
public abstract class EntityZombieMixin extends Entity {
	@Shadow public abstract void setChild(boolean par1);

	public EntityZombieMixin(World par1World) {
		super(par1World);
	}

	@Inject(method = "onSpawnWithEgg", at = @At("TAIL"))
	public void chanceForBabyZombie(EntityLivingData data, CallbackInfoReturnable<EntityLivingData> cir) {
		if (this.rand.nextFloat() <= 0.05) {
			this.setChild(true);
			if (this.worldObj != null) {
				if (this.rand.nextFloat() <= 0.04) {
					EntityChicken chicken = new EntityChicken(this.worldObj);
					((ForceDespawnableEntity) chicken).trueClassic$setDespawnFlag(true);
					chicken.setLocationAndAngles(this.posX, this.posY, this.posZ, MathHelper.wrapAngleTo180_float(this.rotationYaw), rotationPitch);
					worldObj.spawnEntityInWorld(chicken);
					this.mountEntity(chicken);
					chicken.updateRiderPosition();
				}
			}
		}
	}

	@Inject(method = "checkForScrollDrop", at = @At("HEAD"))
	public void addRareDrops(CallbackInfo ci) {
		Item[] items = {Item.potato, BTWItems.carrot, Item.ingotIron};
		for (int i = 0; i < 3; i++) {
			if (this.rand.nextInt(1000) == 0) {
				ItemStack itemstack = new ItemStack(items[i], 1);
				this.entityDropItem(itemstack, 0.0F);
			}
		}
	}
}
