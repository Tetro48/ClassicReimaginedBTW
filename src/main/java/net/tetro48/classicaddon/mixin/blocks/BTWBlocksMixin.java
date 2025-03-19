package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import net.minecraft.src.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BTWBlocks.class)
public class BTWBlocksMixin {
    @Redirect(method = "<clinit>", at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/src/Material;setRequiresTool()Lnet/minecraft/src/Material;"))
    private static Material notRequireAxeOnLog(Material instance) {

        return instance.setAxesEfficientOn();
    }
    @Redirect(method = "<clinit>", at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/src/Material;setRequiresTool()Lnet/minecraft/src/Material;"))
    private static Material notRequireAxeOnWood(Material instance) {

        return instance.setAxesEfficientOn();
    }
    @Redirect(method = "<clinit>", at = @At(ordinal = 5, value = "INVOKE", target = "Lnet/minecraft/src/Material;setRequiresTool()Lnet/minecraft/src/Material;"))
    private static Material notRequireAxeOnWicker(Material instance) {

        return instance.setAxesEfficientOn();
    }
}
