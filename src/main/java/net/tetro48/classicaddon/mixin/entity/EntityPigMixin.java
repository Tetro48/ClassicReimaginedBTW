package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPig.class)
public class EntityPigMixin {
    @Inject(method = "isValidZombieSecondaryTarget", at = @At("RETURN"), cancellable = true)
    public void zombieNoEatAnimal(EntityZombie zombie, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
