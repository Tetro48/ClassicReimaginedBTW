package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public abstract class EntityPlayerMPMixin extends EntityPlayer {

    @Shadow private int exhaustionWithTimeCounter;

    public EntityPlayerMPMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Inject(method = "updateExhaustionWithTime", at = @At("HEAD"))
    public void disableExhaustionWithTime(CallbackInfo ci) {
        this.exhaustionWithTimeCounter--;
    }
}
