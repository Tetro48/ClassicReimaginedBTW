package net.tetro48.classicaddon.mixin.items;

import net.minecraft.src.EnumArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnumArmorMaterial.class)
public abstract class EnumArmorMaterialMixin {
	@ModifyArg(method = "<clinit>", index = 2, at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/src/EnumArmorMaterial;<init>(Ljava/lang/String;II[II)V"))
	private static int changeChainDurability(int original) {
		return 13;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(value = "INVOKE", ordinal = 2, target = "Lnet/minecraft/src/EnumArmorMaterial;<init>(Ljava/lang/String;II[II)V"))
	private static int changeIronDurability(int original) {
		return 13;
	}
}
