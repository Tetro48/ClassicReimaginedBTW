package net.tetro48.classicaddon.mixin.client;

import btw.client.gui.LockButton;
import btw.community.classicaddon.ClassicAddon;
import btw.world.util.difficulty.Difficulties;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.src.*;
import net.tetro48.classicaddon.VanillaDifficultyWorldSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiCreateWorld.class)
public abstract class GuiCreateWorldMixin extends GuiScreen {
	@Shadow private int difficultyID;
	@Shadow private GuiButton buttonDifficultyLevel;

	@Shadow private LockButton buttonLockDifficulty;

	@Shadow private GuiButton moreWorldOptions;
	@Unique private GuiButton buttonVanillaDifficultyLevel;

	@Unique private final String[] vanillaDifficultyNames = new String[] {"easy", "normal", "hard"};

	@Unique private int vanillaDifficultyID = 2;

	@Inject(method = "<init>", at = @At("RETURN"))
	public void forceClassicOnInit(CallbackInfo ci) {
		difficultyID = Difficulties.CLASSIC.index;
	}

	@Inject(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiCreateWorld;updateButtonText()V"))
	public void onInitGui(CallbackInfo ci) {
		buttonDifficultyLevel.enabled = ClassicAddon.cursedDifficultyMode;
		if (ClassicAddon.cursedDifficultyMode) {
			buttonDifficultyLevel.width += 20;
			buttonLockDifficulty.xPosition += 20;
		}
		this.moreWorldOptions.xPosition -= 80;
		buttonVanillaDifficultyLevel = new GuiButton(16, this.width / 2 + 5, 187, 150, 20,
				I18n.getStringParams("classicAddon.selectWorld.vanillaDifficulty", I18n.getString("options.difficulty.normal")));
		this.buttonList.add(buttonVanillaDifficultyLevel);
	}
	@Inject(method = "actionPerformed", at = @At("HEAD"))
	private void onButtonPress(GuiButton par1GuiButton, CallbackInfo ci) {
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id == 16) {
				vanillaDifficultyID = vanillaDifficultyID % 3 + 1;
				buttonVanillaDifficultyLevel.displayString = I18n.getStringParams("classicAddon.selectWorld.vanillaDifficulty", I18n.getString("options.difficulty." + vanillaDifficultyNames[vanillaDifficultyID-1]));
			}
		}
	}
	@Inject(method = "actionPerformed", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Minecraft;launchIntegratedServer(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/src/WorldSettings;)V"))
	private void beforeIntegratedServerStart(GuiButton par1GuiButton, CallbackInfo ci, long seed, String var4, EnumGameType gameType, WorldSettings settings) {
		((VanillaDifficultyWorldSetting)(Object)settings).classicReimagined$setVanillaDifficultyID(this.vanillaDifficultyID);
	}

	@Inject(method = "updateButtonText", at = @At("RETURN"))
	public void changeDifficultyText(CallbackInfo ci) {
		if (difficultyID != Difficulties.CLASSIC.index) {
			this.buttonDifficultyLevel.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", Difficulties.DIFFICULTY_LIST.get(this.difficultyID).getLocalizedName());
			if (FabricLoader.getInstance().isModLoaded("nightmare_mode")) {
				if (difficultyID == Difficulties.HOSTILE.index) {
					this.buttonDifficultyLevel.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", I18n.getString("classicAddon.selectWorld.nmCompat.nightmare"));
				}
				else if (difficultyID == Difficulties.STANDARD.index) {
					this.buttonDifficultyLevel.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", I18n.getString("classicAddon.selectWorld.nmCompat.baddream"));
				}
			}
		}
		else {
			this.buttonDifficultyLevel.displayString += "+";
		}
	}
	@Inject(method = "func_82288_a", at = @At("TAIL"))
	public void hideDifficultyLock(boolean par1, CallbackInfo ci) {
		buttonLockDifficulty.drawButton = !par1;
	}
}
