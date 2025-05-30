package net.tetro48.classicaddon.mixin.items;

import btw.item.items.HoeItem;
import btw.item.items.ToolItem;
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

	@Inject(method = "applyStandardEfficiencyModifiers", at = @At("RETURN"), remap = false)
	private void undoModifiers(CallbackInfo ci) {
		super.applyStandardEfficiencyModifiers();
	}
}
