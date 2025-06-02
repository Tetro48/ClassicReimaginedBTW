package net.tetro48.classicaddon.mixin.entity;

import btw.item.BTWItems;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySheep.class)
public abstract class EntitySheepMixin {
    @Inject(method = "isValidZombieSecondaryTarget", at = @At("RETURN"), cancellable = true)
    public void zombieNoEatAnimal(EntityZombie zombie, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void changeBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stack.itemID == BTWItems.wheat.itemID);
    }
}
