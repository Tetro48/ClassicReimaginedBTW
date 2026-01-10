package net.tetro48.classicaddon;

import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.data.EmiRemoveFromIndex;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public interface InterfaceItemEMI {
	default Item classicReimagined$revealToEMI() {
		if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
			for (int i = 0; i < 16; ++i) {
				EmiRemoveFromIndex.removed.remove(EmiStack.of(new ItemStack((Item) this, 1, i)));
			}
		}
		return (Item) this;
	}
}
