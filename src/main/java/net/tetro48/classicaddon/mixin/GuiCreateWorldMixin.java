package net.tetro48.classicaddon.mixin;

import btw.world.util.difficulty.Difficulties;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiCreateWorld;
import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCreateWorld.class)
public abstract class GuiCreateWorldMixin {
    @Shadow private int difficultyID;
    @Shadow private GuiButton buttonDifficultyLevel;

    @Inject(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiCreateWorld;updateButtonText()V"))
    public void forceClassicOnInit(CallbackInfo ci) {
        difficultyID = Difficulties.CLASSIC.ID;
        buttonDifficultyLevel.enabled = false;
    }
}
