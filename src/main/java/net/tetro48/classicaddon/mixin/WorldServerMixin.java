package net.tetro48.classicaddon.mixin;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldServer.class)
public abstract class WorldServerMixin extends World {
	@Shadow @Final private MinecraftServer mcServer;

	public WorldServerMixin(ISaveHandler par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler, ILogAgent par6ILogAgent) {
		super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/WorldChunkManager;cleanupCache()V"))
	private void setVanillaDifficulty(CallbackInfo ci) {
		this.difficultySetting = this.mcServer.worldServers[0].getData(ClassicAddon.VANILLA_DIFFICULTY_LEVEL);
	}
}
