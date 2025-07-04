package net.tetro48.classicaddon.mixin.entity;

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
	@Redirect(method = "attackEntityFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityEnderman;teleportRandomly()Z"))
	private boolean doNotTeleport(EntityEnderman instance) {
		return true;
	}

	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;isDaytime()Z"))
	private boolean doNotDeaggroInDaytime(World instance) {
		return false;
	}
}
