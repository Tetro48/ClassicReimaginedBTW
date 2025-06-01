package net.tetro48.classicaddon.mixin;

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
                this.drawString(this.mc.fontRenderer, String.format("Coordinates: %.3f / %.3f / %.3f (%.2f, %.2f)", mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw), MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch)), 2, y, 0xE0E0E0);
            }
            else {
                this.drawString(this.mc.fontRenderer, "Hold the compass to view coordinates.", 2, y, 0xFF9090);
            }
        }
    }
}
