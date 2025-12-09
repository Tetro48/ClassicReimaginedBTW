package net.tetro48.classicaddon.mixin.items;

import api.item.items.HoeItem;
import api.item.items.ToolItem;
import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin extends ToolItem {
	protected HoeItemMixin(int iItemID, int iBaseEntityDamage, EnumToolMaterial par3EnumToolMaterial) {
		super(iItemID, iBaseEntityDamage, par3EnumToolMaterial);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void undoModifiers(int itemID, EnumToolMaterial material, CallbackInfo ci) {
		efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
	}
}
