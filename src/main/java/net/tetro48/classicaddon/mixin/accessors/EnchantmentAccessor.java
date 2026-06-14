package net.tetro48.classicaddon.mixin.accessors;

import net.minecraft.src.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Enchantment.class)
public interface EnchantmentAccessor {
	@Accessor("canBeAppliedByVanillaEnchanter")
	void setCanBeAppliedByVanillaEnchanter(boolean bool);
}
