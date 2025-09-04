package net.tetro48.classicaddon.mixin.client;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.src.GuiRepair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiRepair.class)
public abstract class GuiRepairMixin {
	@ModifyConstant(method = "drawGuiContainerForegroundLayer", constant = @Constant(intValue = 40))
	private int noMaximumCost(int constant) {
		return ClassicAddon.yeetTooExpensive ? Integer.MAX_VALUE : constant;
	}
}
