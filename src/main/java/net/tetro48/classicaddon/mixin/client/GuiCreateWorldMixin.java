package net.tetro48.classicaddon.mixin.client;

import btw.client.gui.LockButton;
import btw.community.classicaddon.ClassicAddon;
import btw.world.util.difficulty.Difficulties;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiCreateWorld;
import net.minecraft.src.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCreateWorld.class)
public abstract class GuiCreateWorldMixin {
	@Shadow private int difficultyID;
	@Shadow private GuiButton buttonDifficultyLevel;

	@Shadow private LockButton buttonLockDifficulty;

	@Inject(method = "<init>", at = @At("RETURN"))
	public void forceClassicOnInit(CallbackInfo ci) {
		difficultyID = Difficulties.CLASSIC.ID;
	}

	@Inject(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiCreateWorld;updateButtonText()V"))
	public void onInitGui(CallbackInfo ci) {
		buttonDifficultyLevel.enabled = ClassicAddon.cursedDifficultyMode;
		if (ClassicAddon.cursedDifficultyMode) {
			buttonDifficultyLevel.width += 20;
			buttonLockDifficulty.xPosition += 20;
		}
	}
	@Inject(method = "updateButtonText", at = @At("RETURN"))
	public void changeDifficultyText(CallbackInfo ci) {
		if (difficultyID != Difficulties.CLASSIC.ID) {
			this.buttonDifficultyLevel.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", Difficulties.DIFFICULTY_LIST.get(this.difficultyID).getLocalizedName());
			if (FabricLoader.getInstance().isModLoaded("nightmare_mode")) {
				if (difficultyID == Difficulties.HOSTILE.ID) {
					this.buttonDifficultyLevel.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", I18n.getString("classicAddon.selectWorld.nmCompat.nightmare"));
				}
				else if (difficultyID == Difficulties.STANDARD.ID) {
					this.buttonDifficultyLevel.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", I18n.getString("classicAddon.selectWorld.nmCompat.baddream"));
				}
			}
		}
	}
	@Inject(method = "func_82288_a", at = @At("TAIL"))
	public void hideDifficultyLock(boolean par1, CallbackInfo ci) {
		buttonLockDifficulty.drawButton = !par1;
	}
}
