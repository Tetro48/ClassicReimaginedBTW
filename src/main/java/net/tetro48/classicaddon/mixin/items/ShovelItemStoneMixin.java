package net.tetro48.classicaddon.mixin.items;

import api.item.items.ShovelItem;
import btw.item.items.ShovelItemStone;
import net.minecraft.src.EnumToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShovelItemStone.class)
public abstract class ShovelItemStoneMixin extends ShovelItem {
	public ShovelItemStoneMixin(int iItemID, EnumToolMaterial material) {
		super(iItemID, material);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void undoModifiers(int iItemID, CallbackInfo ci) {
		efficiencyOnProperMaterial = EnumToolMaterial.STONE.getEfficiencyOnProperMaterial();
	}
}
