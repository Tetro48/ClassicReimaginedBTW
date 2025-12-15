package net.tetro48.classicaddon.mixin;

import net.minecraft.src.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiTextField.class)
public interface GuiTextFieldAccessor {
	@Mutable
	@Accessor("yPos")
	void setYPos(int pos);
}
