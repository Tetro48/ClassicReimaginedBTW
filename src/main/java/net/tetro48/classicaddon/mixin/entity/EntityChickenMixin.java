package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.tetro48.classicaddon.ForceDespawnableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityChicken.class)
public abstract class EntityChickenMixin extends EntityAnimal implements ForceDespawnableEntity {
	@Unique
	public boolean despawnFlag = false;
	public EntityChickenMixin(World par1World) {
		super(par1World);
	}
	@Inject(method = "writeEntityToNBT", at = @At("TAIL"))
	private void writeNewNBT(NBTTagCompound tag, CallbackInfo ci) {
		tag.setBoolean("tcDespawnFlag", despawnFlag);
	}
	@Inject(method = "readEntityFromNBT", at = @At("TAIL"))
	private void readNewNBT(NBTTagCompound tag, CallbackInfo ci) {
		if (tag.hasKey("tcDespawnFlag")) {
			despawnFlag = tag.getBoolean("tcDespawnFlag");
		}
	}

	@Override
	protected boolean canDespawn() {
		return despawnFlag;
	}

	@Override
	public void trueClassic$setDespawnFlag(boolean despawnFlag) {
		this.despawnFlag = despawnFlag;
	}
}
