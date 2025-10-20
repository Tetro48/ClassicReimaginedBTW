package net.tetro48.classicaddon.mixin;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.IntegratedServer;
import net.minecraft.src.Minecraft;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;
import net.tetro48.classicaddon.VanillaDifficultyWorldSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin extends MinecraftServer {
	public IntegratedServerMixin(File par1File) {
		super(par1File);
	}

	@Unique
	private int onLoadDifficultyID;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(Minecraft par1Minecraft, String par2Str, String par3Str, WorldSettings par4WorldSettings, CallbackInfo ci) {
		this.onLoadDifficultyID = ((VanillaDifficultyWorldSetting)(Object)par4WorldSettings).classicReimagined$getVanillaDifficultyID();
	}
	@Inject(method = "loadAllWorlds", at = @At("TAIL"))
	private void onWorldLoad(String par1Str, String par2Str, long par3, WorldType par5WorldType, String par6Str, CallbackInfo ci) {
		if (onLoadDifficultyID != -1) {
			this.worldServers[0].setData(ClassicAddon.VANILLA_DIFFICULTY_LEVEL, onLoadDifficultyID);
		}
	}
}
