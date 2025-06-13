package net.tetro48.classicaddon.mixin.entity;

import btw.community.classicaddon.ClassicAddon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerMP.class)
public abstract class EntityPlayerMPMixin extends EntityPlayer {

    @Shadow private int exhaustionWithTimeCounter;

    @Unique
    private static double[] visualMoonBrightnessByPhase = new double[]{(double)1.25F, (double)0.875F, (double)0.75F, (double)0.5F, (double)0.25F, (double)0.5F, (double)0.75F, (double)1.25F};
    public EntityPlayerMPMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Inject(method = "updateExhaustionWithTime", at = @At("HEAD"))
    public void disableExhaustionWithTime(CallbackInfo ci) {
        this.exhaustionWithTimeCounter--;
    }
    @Inject(method = "isInGloom", at = @At("HEAD"), cancellable = true)
    public void toggleableGloom(CallbackInfoReturnable<Boolean> cir) {
        if (!ClassicAddon.gloomToggle) cir.setReturnValue(false);
    }
    @Redirect(method = "isInGloom", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;computeOverworldSunBrightnessWithMoonPhases()F"))
    private float notGloomInVisualNewMoon(World instance) {
        long lOffsetWorldTime = instance.worldInfo.getWorldTime() - 12000L;
        if (lOffsetWorldTime < 0L) {
            lOffsetWorldTime = 0L;
        }

        int iMoonPhase = (int)(lOffsetWorldTime / 24000L % 8L);
        double dMoonBrightness = visualMoonBrightnessByPhase[iMoonPhase];
        float fCelestialAngle = instance.getCelestialAngle(1.0F);
        float fSunInvertedBrightness = 1.0F - (MathHelper.cos(fCelestialAngle * (float)Math.PI * 2.0F) * 2.0F + 0.25F);
        if (fSunInvertedBrightness < 0.0F) {
            fSunInvertedBrightness = 0.0F;
        } else if (fSunInvertedBrightness > 1.0F) {
            fSunInvertedBrightness = 1.0F;
        }

        double dSunBrightness = 1.0d - (double)fSunInvertedBrightness;
        double dRainBrightnessModifier = 1.0d - (double)(instance.getRainStrength(1.0F) * 5.0F) / 16.0d;
        double dStormBrightnessModifier = 1.0d - (double)(instance.getWeightedThunderStrength(1.0F) * 5.0F) / 16.0d;
        dSunBrightness = dSunBrightness * dRainBrightnessModifier * dStormBrightnessModifier;
        double dMinBrightness = 0.2;
        dMinBrightness *= dMoonBrightness * dRainBrightnessModifier * dStormBrightnessModifier;
        if (dMinBrightness < 0.05) {
            dMinBrightness = 0.0d;
        }

        return (float)(dSunBrightness * (1.0d - dMinBrightness) + dMinBrightness);
    }
}
