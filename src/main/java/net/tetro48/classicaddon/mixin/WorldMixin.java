package net.tetro48.classicaddon.mixin;

import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(World.class)
public abstract class WorldMixin {
	@ModifyConstant(method = "<clinit>", constant = @Constant(doubleValue = 0d, ordinal = 0))
	private static double noGloomNights(double constant) {
		return 0.25d;
	}
}
