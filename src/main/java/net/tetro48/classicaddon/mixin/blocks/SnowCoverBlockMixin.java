package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.SnowCoverBlock;
import net.minecraft.src.Entity;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowCoverBlock.class)
public abstract class SnowCoverBlockMixin {
	@Inject(method = "onEntityCollidedWithBlock", at = @At("HEAD"), cancellable = true)
	private void stopSlowdown(World world, int i, int j, int k, Entity entity, CallbackInfo ci) {
		ci.cancel();
	}
}
