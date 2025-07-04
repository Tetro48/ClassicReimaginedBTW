package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {
	@Redirect(method = "getSpeedModifier", at = @At(value = "INVOKE", target = "Lbtw/world/util/WorldUtils;isGroundCoverOnBlock(Lnet/minecraft/src/IBlockAccess;III)Z"))
	private boolean noSlowdownFromGroundCovers(IBlockAccess blockAbove, int blockAccess, int i, int j) {
		return false;
	}
}
