package net.tetro48.classicaddon.mixin.client;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.Minecraft;
import net.minecraft.src.NetClientHandler;
import net.tetro48.classicaddon.ServerCheckGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(NetClientHandler.class)
public abstract class NetClientHandlerMixin {
	@Shadow private Minecraft mc;

	@ModifyArg(method = "updateTerrainLoad", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Minecraft;displayGuiScreen(Lnet/minecraft/src/GuiScreen;)V"))
	private GuiScreen sendToServerCheckGui(GuiScreen par1GuiScreen) {
		return new ServerCheckGui(par1GuiScreen, this.mc);
	}
}
