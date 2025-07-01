package net.tetro48.classicaddon.mixin.items;

import net.minecraft.src.Item;
import net.minecraft.src.ItemDoor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDoor.class)
public abstract class ItemDoorMixin extends Item {
	public ItemDoorMixin(int par1) {
		super(par1);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void changeStackSize(CallbackInfo ci) {
		this.maxStackSize = 64;
	}
}
