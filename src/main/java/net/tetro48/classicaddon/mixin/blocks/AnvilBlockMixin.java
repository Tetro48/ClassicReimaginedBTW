package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.AnvilBlock;
import btw.world.util.WorldUtils;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin extends Block {

	protected AnvilBlockMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}
	@Inject(method = "<init>", at = @At("RETURN"))
	private void vanillaifyToughness(int iBlockID, CallbackInfo ci) {
		this.setHardness(5.0F);
		this.setResistance(400.0F);
	}
	@Redirect(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;playAuxSFX(IIIII)V"))
	private void plsNoNoise(World instance, int par1, int par2, int par3, int par4, int par5) {

	}
	@Inject(method = "onBlockActivated", at = @At("HEAD"), cancellable = true)
	private void vanillaAnvilFunctionality(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick, CallbackInfoReturnable<Boolean> cir){
		if (!world.isRemote && !WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j + 1, k, 0) && player instanceof EntityPlayerMP) {
			player.displayGUIAnvil(i,j,k);
		}
		cir.setReturnValue(true);
	}
	@Inject(method = "canPlaceBlockAt", at = @At("HEAD"), cancellable = true)
	private void forcePlaceable(World world, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
		int var5 = world.getBlockId(i, j, k);
		cir.setReturnValue(var5 == 0 || blocksList[var5].blockMaterial.isReplaceable());
	}
	@Inject(method = "onNeighborBlockChange", at = @At("HEAD"), cancellable = true)
	private void makeAnvilFall(World world, int i, int j, int k, int iBlockID, CallbackInfo ci) {
		this.scheduleCheckForFall(world, i, j, k);
		ci.cancel();
	}
	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDropType(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.anvil.blockID);
	}

	@Inject(method = "quantityDropped", at = @At("RETURN"), cancellable = true)
	public void changeDropQuantity(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(1);
	}

	public void onBlockAdded(World world, int i, int j, int k) {
		this.scheduleCheckForFall(world, i, j, k);
	}

	public void updateTick(World world, int i, int j, int k, Random rand) {
		this.checkForFall(world, i, j, k);
	}

	public boolean onFinishedFalling(EntityFallingSand entity, float fFallDistance) {
		boolean willAnvilBreak = fFallDistance > 40;
		entity.playSound(willAnvilBreak ? "random.anvil_break" : "random.anvil_land", 0.5f, 1f + entity.rand.nextFloat() * 0.1f);
		return !willAnvilBreak;
	}

	public int tickRate(World par1World) {
		return 2;
	}

	protected void onStartFalling(EntityFallingSand entity) {
		entity.setIsAnvil(true);
	}
}
