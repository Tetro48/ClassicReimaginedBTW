package net.tetro48.classicaddon.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EntitySpider.class)
public class EntitySpiderMixin {

    @Inject(method = "findPlayerToAttack", at = @At("RETURN"), cancellable = true)
    protected void noHuntingChickens(CallbackInfoReturnable<Entity> cir) {
        if (cir.getReturnValue() instanceof EntityChicken)
            cir.setReturnValue(null);
    }
}
