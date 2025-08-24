package net.tetro48.classicaddon.mixin;

import net.minecraft.src.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Material.class)
public abstract class MaterialMixin {
	@Redirect(method = "<clinit>", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/src/Material;setRequiresTool()Lnet/minecraft/src/Material;"))
	private static Material doNotRequireToolForGrass(Material instance){
		return instance;
	}
	@Redirect(method = "<clinit>", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/src/Material;setRequiresTool()Lnet/minecraft/src/Material;"))
	private static Material doNotRequireToolForGround(Material instance){
		return instance;
	}
}
