package net.tetro48.classicaddon.mixin.items;

import btw.item.blockitems.FreshPumpkinBlockItem;
import net.minecraft.src.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FreshPumpkinBlockItem.class)
public abstract class FreshPumpkinBlockItemMixin extends ItemBlock {
	public FreshPumpkinBlockItemMixin(int par1) {
		super(par1);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void changeStackSize(int iBlockID, CallbackInfo ci) {
		this.setMaxStackSize(64);
	}
}
