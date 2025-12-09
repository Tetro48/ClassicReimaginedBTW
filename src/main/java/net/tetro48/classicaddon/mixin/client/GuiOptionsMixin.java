package net.tetro48.classicaddon.mixin.client;

import api.world.difficulty.Difficulty;
import btw.world.BTWDifficulties;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiOptions.class)
public abstract class GuiOptionsMixin {
	@Inject(method = "initGui", at = @At(value = "FIELD", target = "Lnet/minecraft/src/GuiSmallButton;displayString:Ljava/lang/String;",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILHARD)
	private void manageIngameDifficultyDisplay(CallbackInfo ci, int var1, EnumOptions[] var2, int var3, int var4, EnumOptions var5, GuiSmallButton var6){
		Difficulty difficulty = MinecraftServer.getServer().worldServers[0].worldInfo.getDifficulty();
		var6.displayString = I18n.getString("selectWorld.difficulty") + ": " + difficulty.getLocalizedName();
		var6.width += 20;
		if (difficulty != BTWDifficulties.CLASSIC) {
			var6.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", difficulty.getLocalizedName());
			if (FabricLoader.getInstance().isModLoaded("nightmare_mode")) {
				if (difficulty == BTWDifficulties.HOSTILE) {
					var6.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", I18n.getString("classicAddon.selectWorld.nmCompat.nightmare"));
				}
				else if (difficulty == BTWDifficulties.STANDARD) {
					var6.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", I18n.getString("classicAddon.selectWorld.nmCompat.baddream"));
				}
			}
		}
		else {
			var6.displayString += "+";
		}
	}
	@Inject(method = "actionPerformed", at = @At("RETURN"))
	private void manageActionPerformedIngameDifficultyDisplay(GuiButton par1GuiButton, CallbackInfo ci) {
		if (EnumOptions.getEnumOptions(par1GuiButton.id) != EnumOptions.DIFFICULTY) {
			return;
		}
		Difficulty difficulty = MinecraftServer.getServer().worldServers[0].worldInfo.getDifficulty();
		par1GuiButton.displayString = I18n.getString("selectWorld.difficulty") + ": " + difficulty.getLocalizedName();
		if (difficulty != BTWDifficulties.CLASSIC) {
			par1GuiButton.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", difficulty.getLocalizedName());
			if (FabricLoader.getInstance().isModLoaded("nightmare_mode")) {
				if (difficulty == BTWDifficulties.HOSTILE) {
					par1GuiButton.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", I18n.getString("classicAddon.selectWorld.nmCompat.nightmare"));
				}
				else if (difficulty == BTWDifficulties.STANDARD) {
					par1GuiButton.displayString = I18n.getString("selectWorld.difficulty") + ": " + I18n.getStringParams("classicAddon.selectWorld.cursedDifficulty", I18n.getString("classicAddon.selectWorld.nmCompat.baddream"));
				}
			}
		}
		else {
			par1GuiButton.displayString += "+";
		}
	}
}
