package net.tetro48.classicaddon;

import net.minecraft.src.Item;

public interface InterfaceItemEMI {
	default Item trueClassic$revealToEMI() {
		return null;
	}
}
