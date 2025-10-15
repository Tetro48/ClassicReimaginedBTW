package net.tetro48.classicaddon.mixin.entity;

import btw.entity.mob.KickingAnimal;
import btw.item.BTWItems;
import btw.world.util.difficulty.DifficultyParam;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityCow.class)
public abstract class EntityCowMixin extends KickingAnimal {
	public EntityCowMixin(World par1World) {
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
	@Inject(method = "getDropItemId", at = @At("HEAD"), cancellable = true)
	private void setDropItemIdToLeather(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Item.leather.itemID);
	}
	@Inject(method = "dropFewItems", at = @At("HEAD"), cancellable = true)
	private void returnDropsToNormal(boolean killedByPlayer, int lootingModifier, CallbackInfo ci) {
		if (!isStarving()) {
			int numDrops = rand.nextInt(3) + rand.nextInt(1 + lootingModifier) + 1;

			if (isFamished()) {
				numDrops = numDrops / 2;
			}

			for (int i = 0; i < numDrops; ++i) {
				dropItem(Item.leather.itemID, 1);
			}

			if (!hasHeadCrabbedSquid()) {
				numDrops = rand.nextInt(3) + 1 + rand.nextInt(1 + lootingModifier);

				if (isFamished()) {
					numDrops = numDrops / 2;
				}

				for (int iTempCount = 0; iTempCount < numDrops; ++iTempCount) {
					if (isBurning()) {
						if (worldObj.getDifficultyParameter(DifficultyParam.ShouldBurningAnimalsDropCookedMeat.class)) {
							dropItem(Item.beefCooked.itemID, 1);
						}
						else {
							dropItem(BTWItems.burnedMeat.itemID, 1);
						}
					}
					else {
						dropItem(Item.beefRaw.itemID, 1);
					}
				}
			}
		}
		ci.cancel();
	}
}
