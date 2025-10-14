package net.tetro48.classicaddon.mixin.entity;

import btw.community.classicaddon.ClassicAddon;
import btw.item.BTWItems;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

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
	@ModifyArgs(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(III)V"))
	private void modifyWoolShearDrop(Args args) {
		args.set(0, Block.cloth.blockID);
		args.set(2, 15 - (int)args.get(2));
	}
	@ModifyArgs(method = "onBlockDispenserConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(III)V"))
	private void modifyWoolSuckDrop(Args args) {
		args.set(0, Block.cloth.blockID);
		args.set(2, 15 - (int)args.get(2));
	}
	@ModifyArgs(method = "initiateWolfBomb", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(III)V"))
	private void modifyWoolBombDrop(Args args) {
		args.set(0, Block.cloth.blockID);
		args.set(2, 15 - (int)args.get(2));
	}
	@ModifyArgs(method = "dropFewItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(III)V"))
	private void modifyWoolDeathDrop(Args args) {
		args.set(0, Block.cloth.blockID);
		args.set(2, 15 - (int)args.get(2));
	}
	@Inject(method = "getDropItemId", at = @At("HEAD"), cancellable = true)
	private void modifyWoolIdDrop(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.cloth.blockID);
	}
	@Inject(method = "onLivingUpdate", at = @At("HEAD"))
	private void tick(CallbackInfo ci) {
		ticksUntilGraze--;
		if (!ClassicAddon.animageddonToggle && getSheared()) {
			woolAccumulationCount--;
			ticksUntilGraze -= 7;
		}
	}
	@Override
	public void onGrazeBlock(int i, int j, int k) {
		super.onGrazeBlock(i, j, k);
		ticksUntilGraze = rand.nextInt(2400) + 800;
		if (!ClassicAddon.animageddonToggle && getSheared()) woolAccumulationCount = 25000;
	}
	@Override
	public boolean isHungryEnoughToGraze() {
		if (ClassicAddon.animageddonToggle) {
			return super.isHungryEnoughToGraze();
		}
		return ticksUntilGraze <= 0;
	}
}
