package net.tetro48.classicaddon.mixin.accessors;

import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.data.EmiData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(EmiData.class)
public interface EmiDataAccessor {
	@Accessor("hiddenStacks")
	static List<EmiIngredient> getHiddenStacks() {
		throw new AssertionError();
	}
}
