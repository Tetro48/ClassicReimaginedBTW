package net.tetro48.classicaddon.mixin.entity;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityHorse.class)
public abstract class EntityHorseMixin extends EntityAnimal {
	@Shadow protected abstract void func_110237_h(EntityPlayer par1EntityPlayer);

	@Shadow public abstract boolean isAdultHorse();

	public EntityHorseMixin(World par1World) {
		super(par1World);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void addNewAITask(World par1World, CallbackInfo ci) {
		this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy((EntityHorse)(Object)this, 1.2));
	}

	@Inject(method = "isValidZombieSecondaryTarget", at = @At("RETURN"), cancellable = true)
	public void zombieNoEatAnimal(EntityZombie zombie, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
	@Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
	private void changeBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(stack.itemID == Item.appleGold.itemID || stack.itemID == Item.goldenCarrot.itemID);
	}

	@Inject(method = "interact", at = @At(value = "RETURN", ordinal = 7), cancellable = true)
	private void allowHorseMounting(EntityPlayer player, CallbackInfoReturnable<Boolean> cir){
		ItemStack item = player.inventory.getCurrentItem();
		if (this.isAdultHorse() && this.riddenByEntity == null) {
			if (item == null || !item.func_111282_a(player, this)) {
				this.func_110237_h(player);
			}
			cir.setReturnValue(true);
		}
	}
}
