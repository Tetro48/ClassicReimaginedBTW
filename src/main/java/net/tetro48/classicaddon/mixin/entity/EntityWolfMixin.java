package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityWolf;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityWolf.class)
public abstract class EntityWolfMixin {
	@Unique
	public boolean isWolfFed = false;

	@Redirect(method = "updateShitState", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityWolf;isFullyFed()Z"))
	private boolean modifyShitState(EntityWolf instance) {
		return isWolfFed;
	}

	@Inject(method = "attemptToShit", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;spawnEntityInWorld(Lnet/minecraft/src/Entity;)Z"))
	private void onSuccessfulShit(CallbackInfoReturnable<Boolean> cir) {
		isWolfFed = false;
	}

	@Inject(method = "onEat", at = @At("RETURN"))
	private void makeWolfFed(Item food, CallbackInfo ci) {
		isWolfFed = true;
	}
	@Inject(method = "writeEntityToNBT", at = @At("TAIL"))
	private void writeEntityToNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
		par1NBTTagCompound.setBoolean("tcIsWolfFed", isWolfFed);
	}
	@Inject(method = "readEntityFromNBT", at = @At("TAIL"))
	private void readEntityFromNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
		if (par1NBTTagCompound.hasKey("tcIsWolfFed")) {
			isWolfFed = par1NBTTagCompound.getBoolean("tcIsWolfFed");
		}
	}
	@Inject(method = "isHungryEnoughToEatFood", at = @At("RETURN"), cancellable = true)
	private void canWolfEat(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(!isWolfFed);
	}
}
