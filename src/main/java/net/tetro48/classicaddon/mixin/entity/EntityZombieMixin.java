package net.tetro48.classicaddon.mixin.entity;

import btw.item.BTWItems;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityZombie.class)
public abstract class EntityZombieMixin extends Entity {
    public EntityZombieMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "checkForScrollDrop", at = @At("HEAD"))
    public void addRareDrops(CallbackInfo ci) {
        Item[] items = {Item.potato, BTWItems.carrot, Item.ingotIron};
        for (int i = 0; i < 3; i++) {
            if (this.rand.nextInt(1000) == 0) {
                ItemStack itemstack = new ItemStack(items[i], 1);
                this.entityDropItem(itemstack, 0.0F);
            }
        }
    }
}
