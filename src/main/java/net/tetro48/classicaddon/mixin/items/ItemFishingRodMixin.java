package net.tetro48.classicaddon.mixin.items;

import net.minecraft.src.Item;
import net.minecraft.src.ItemFishingRod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFishingRod.class)
public abstract class ItemFishingRodMixin extends Item {
	public ItemFishingRodMixin(int par1) {
		super(par1);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void changeDurability(CallbackInfo ci) {
		this.setMaxDamage(64);
	}
}
