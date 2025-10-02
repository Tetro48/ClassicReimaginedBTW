package net.tetro48.classicaddon.mixin;

import net.minecraft.src.ItemInWorldManager;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInWorldManager.class)
public abstract class ItemInWorldManagerMixin {
	@Shadow public World theWorld;

	@Shadow public abstract boolean isCreative();

	@Inject(method = "onBlockClicked", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/ItemInWorldManager;isCreative()Z"))
	private void extinguishFireNoMatterWhat(int par1, int par2, int par3, int par4, CallbackInfo ci) {
		if (!this.isCreative()) {
			this.theWorld.extinguishFire(null, par1, par2, par3, par4);
		}
	}
}
