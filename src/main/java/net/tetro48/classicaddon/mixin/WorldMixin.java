package net.tetro48.classicaddon.mixin;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
	@Shadow private static double[] moonBrightnessByPhase;
	@Inject(method = "tick", at = @At("HEAD"))
	private void newMoonShenanigans(CallbackInfo ci) {
		if (ClassicAddon.isServerRunningThisAddon || MinecraftServer.getIsServer()) moonBrightnessByPhase[4] = 0.25d;
		else moonBrightnessByPhase[4] = 0d;
	}
}
