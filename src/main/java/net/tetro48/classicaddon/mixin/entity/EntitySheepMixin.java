package net.tetro48.classicaddon.mixin.entity;

import btw.community.classicaddon.ClassicAddon;
import btw.item.BTWItems;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySheep.class)
public abstract class EntitySheepMixin extends EntityAnimal {
    @Shadow private int woolAccumulationCount;

    @Shadow public abstract boolean getSheared();

    @Unique private int ticksUntilGraze = 0;

    public EntitySheepMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "isValidZombieSecondaryTarget", at = @At("RETURN"), cancellable = true)
    public void zombieNoEatAnimal(EntityZombie zombie, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void changeBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stack.itemID == BTWItems.wheat.itemID);
    }
    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ticksUntilGraze--;
    }
    @Override
    public void onGrazeBlock(int i, int j, int k) {
        super.onGrazeBlock(i, j, k);
        ticksUntilGraze = rand.nextInt(600) + 200;
        if (!ClassicAddon.animageddonToggle && getSheared()) woolAccumulationCount = 24000;
    }
    @Override
    public boolean isHungryEnoughToGraze() {
        if (ClassicAddon.animageddonToggle) {
            return super.isHungryEnoughToGraze();
        }
        return ticksUntilGraze <= 0;
    }
}
