package net.tetro48.classicaddon.mixin.client;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;

@Mixin(GuiIngame.class)
public abstract class GuiIngameMixin extends Gui {
	@Shadow @Final private Minecraft mc;
	@Unique public boolean hasFullSaturationShank = false;
	@ModifyArg(method = "drawFoodOverlay", index = 2, at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/GuiIngame;drawTexturedModalRect(IIIIII)V"))
	public int getVariableThing(int par1) {
		hasFullSaturationShank = par1 == 25;
		return par1;
	}
	@Inject(method = "drawFoodOverlay", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/GuiIngame;drawTexturedModalRect(IIIIII)V"))
	public void changeSaturationPipColor(int iScreenX, int iScreenY, CallbackInfo ci) {
		if (hasFullSaturationShank) GL11.glColor3d(0.75d, 0.75d, 0d);
	}
	@Inject(method = "drawFoodOverlay", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/GuiIngame;drawTexturedModalRect(IIIIII)V", shift = At.Shift.AFTER))
	public void postChangeSaturationPipColor(int iScreenX, int iScreenY, CallbackInfo ci) {
		GL11.glColor3d(1d, 1d, 1d);
	}
	@Inject(method = "drawFoodOverlay", at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/src/GuiIngame;drawTexturedModalRect(IIIIII)V"))
	public void changePartialSaturationPipColor(int iScreenX, int iScreenY, CallbackInfo ci) {
		GL11.glColor3d(0.75d, 0.75d, 0d);
	}
	@Inject(method = "drawFoodOverlay", at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/src/GuiIngame;drawTexturedModalRect(IIIIII)V", shift = At.Shift.AFTER))
	public void postChangePartialSaturationPipColor(int iScreenX, int iScreenY, CallbackInfo ci) {
		GL11.glColor3d(1d, 1d, 1d);
	}
	@Inject(method = "renderModDebugOverlay", at = @At("RETURN"))
	private void addCoordinateDisplay(int y, CallbackInfo ci) {
		boolean isPlayerHoldingCompass = mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().itemID == Item.compass.itemID;
		y += 10;
		if (MinecraftServer.getServer() != null)
			y += 40;
		if (ClassicAddon.isServerRunningThisAddon) {
			if (isPlayerHoldingCompass) {
				String direction;
				String string4;
				float yaw = MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw);
				int directionID = MathHelper.floor_double(yaw / 90.0d + 0.5d) & 3;
				if (directionID == 0) {
					direction = "+Z";
					string4 = "S";
				} else if (directionID == 1) {
					direction = "-X";
					string4 = "W";
				} else if (directionID == 2) {
					direction = "-Z";
					string4 = "N";
				} else {
					direction = "+X";
					string4 = "E";
				}
				this.drawString(this.mc.fontRenderer, String.format(java.util.Locale.ROOT, "XYZ: %.3f / %.3f / %.3f", this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY, this.mc.thePlayer.posZ), 2, y, 0xE0E0E0);
				this.drawString(this.mc.fontRenderer, String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, string4, yaw, MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationPitch)), 2, y + 10, 0xE0E0E0);
			}
			else {
				this.drawString(this.mc.fontRenderer, "Hold the compass to view coordinates.", 2, y, 0xFF9090);
			}
		}
	}
}
