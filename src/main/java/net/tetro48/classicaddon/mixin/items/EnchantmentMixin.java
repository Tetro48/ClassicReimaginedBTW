package net.tetro48.classicaddon.mixin.items;

import net.minecraft.src.Enchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
	@Shadow @Final public static Enchantment protection;

	@Shadow @Final public static Enchantment sharpness;

	@Shadow @Final public static Enchantment silkTouch;

	@Shadow @Final public static Enchantment fortune;

	@Shadow @Final public static Enchantment featherFalling;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void makeEnchantmentsApplicable(CallbackInfo ci) {
		protection.canBeAppliedByVanillaEnchanter = true;
		silkTouch.canBeAppliedByVanillaEnchanter = true;
		sharpness.canBeAppliedByVanillaEnchanter = true;
		fortune.canBeAppliedByVanillaEnchanter = true;
		featherFalling.canBeAppliedByVanillaEnchanter = true;
	}
}
