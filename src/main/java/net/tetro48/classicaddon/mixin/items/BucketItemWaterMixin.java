package net.tetro48.classicaddon.mixin.items;

import api.util.MiscUtils;
import btw.community.classicaddon.ClassicAddon;
import btw.item.items.BucketItemEmpty;
import btw.item.items.BucketItemWater;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItemWater.class)
public abstract class BucketItemWaterMixin extends Item {
	public BucketItemWaterMixin(int par1) {
		super(par1);
	}

	@Redirect(method = "attemptPlaceContentsAtLocation", at = @At(value = "INVOKE", target = "Lapi/util/MiscUtils;placeNonPersistentWater(Lnet/minecraft/src/World;III)V"))
	private void forceWaterSource(World world, int i, int j, int k) {
		if (ClassicAddon.vanillaifyBuckets) {
			world.setBlockWithNotify(i, j, k, Block.waterMoving.blockID);
		}
		else {
			MiscUtils.placeNonPersistentWater(world, i, j, k);
		}
	}
}
