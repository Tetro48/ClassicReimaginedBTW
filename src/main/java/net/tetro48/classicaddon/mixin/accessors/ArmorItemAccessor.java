package net.tetro48.classicaddon.mixin.accessors;

import api.item.items.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorItem.class)
public interface ArmorItemAccessor {
	@Mutable
	@Accessor("armorWeight")
	void setArmorWeight(int weight);
}
