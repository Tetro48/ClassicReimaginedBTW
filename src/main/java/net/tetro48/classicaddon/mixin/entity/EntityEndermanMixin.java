package net.tetro48.classicaddon.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.EntityEnderman;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityEnderman.class)
public abstract class EntityEndermanMixin {
	@Inject(method = "angerNearbyEndermen", at = @At("HEAD"), cancellable = true)
	private void noAngeringNearbyEndermen(EntityPlayer targetPlayer, CallbackInfo ci) {
		ci.cancel();
	}
	@WrapOperation(method = "attackEntityFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityEnderman;teleportRandomly()Z"))
	private boolean doNotTeleport(EntityEnderman instance, Operation<Boolean> original) {
		return false;
	}

	@WrapOperation(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;isDaytime()Z"))
	private boolean doNotDeaggroInDaytime(World instance, Operation<Boolean> original) {
		return false;
	}
}
