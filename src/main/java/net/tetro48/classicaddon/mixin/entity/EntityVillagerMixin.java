package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityVillager.class)
public abstract class EntityVillagerMixin {
	@Inject(method = "isSecondaryTargetForSquid", at = @At("HEAD"), cancellable = true)
	private void makeSquidNotAttackVillagers(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
}
