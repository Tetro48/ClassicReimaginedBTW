package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityMob.class)
public abstract class EntityMobMixin {
	@Redirect(method = "checkForCatchFireInSun", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityMob;isChild()Z"))
	private boolean burnBabyMobsInSun(EntityMob instance) {
		return false;
	}
}
