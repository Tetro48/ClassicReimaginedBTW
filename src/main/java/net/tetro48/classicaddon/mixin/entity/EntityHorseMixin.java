package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.EntityHorse;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityHorse.class)
public abstract class EntityHorseMixin {
    @Inject(method = "isValidZombieSecondaryTarget", at = @At("RETURN"), cancellable = true)
    public void zombieNoEatAnimal(EntityZombie zombie, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void changeBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stack.itemID == Item.appleGold.itemID || stack.itemID == Item.goldenCarrot.itemID);
    }
}
