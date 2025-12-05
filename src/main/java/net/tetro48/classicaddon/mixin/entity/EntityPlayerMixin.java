package net.tetro48.classicaddon.mixin.entity;

import btw.block.BTWBlocks;
import btw.community.classicaddon.ClassicAddon;
import btw.world.util.difficulty.DifficultyParam;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase {
	public EntityPlayerMixin(World par1World) {
		super(par1World);
	}

	@Shadow public abstract World getEntityWorld();

	@Shadow public abstract void setSpawnChunk(ChunkCoordinates coords, boolean bForced, int iDimension);

	@Shadow private ChunkCoordinates spawnChunk;

	@ModifyArg(method = "addMovementStat", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V"))
	private float returnToVanillaValues(float par1) {
		return par1 / this.getEntityWorld().getDifficultyParameter(DifficultyParam.HungerIntensiveActionCostMultiplier.class);
	}

	@ModifyArg(method = "addMovementStat", at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustionWithoutVisualFeedback(F)V"))
	private float modifyWalkingExhaustion(float fAmount) {
		if (ClassicAddon.modernExhaustionLevels) {
			return 0f;
		}
		return fAmount;
	}

	@ModifyArg(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V"))
	private float modifyAttackExhaustion(float par1) {
		if (ClassicAddon.modernExhaustionLevels) {
			return 0.1f;
		}
		return par1;
	}

	@ModifyArg(method = "addExhaustionForJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V", ordinal = 0))
	private float makeSprintJumpsExpensiveAgain(float par1) {
		if (ClassicAddon.modernExhaustionLevels) {
			return 0.2f;
		}
		return 0.8f;
	}
	@ModifyArg(method = "addExhaustionForJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V", ordinal = 1))
	private float makeJumpsExpensiveAgain(float par1) {
		if (ClassicAddon.modernExhaustionLevels) {
			return 0.05f;
		}
		return 0.2f;
	}

	@Redirect(method = "canEat", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;isPotionActive(Lnet/minecraft/src/Potion;)Z"))
	private boolean makeHungerNotStopYouFromEating(EntityPlayer instance, Potion potion) {
		return false;
	}
	@Inject(method = "canDrink", at = @At("HEAD"), cancellable = true)
	private void makeHungerNotStopYouFromDrinking(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}

	@Inject(method = "sleepInBedAt", at = @At("RETURN"))
	private void setSpawnWhenSleeping(int x, int y, int z, CallbackInfoReturnable<EnumStatus> cir) {
		if (ClassicAddon.shouldBedsSetSpawn && cir.getReturnValue() == EnumStatus.OK && this.worldObj.getBlockId(x, y, z) != BTWBlocks.bedroll.blockID) {
			setSpawnChunk(new ChunkCoordinates(x, y + 1, z), true, this.dimension);
		}
	}
	@Inject(method = "getValidatedRespawnCoordinates", at = @At(ordinal = 17, value = "FIELD", target = "Lnet/minecraft/src/EntityPlayer;spawnChunk:Lnet/minecraft/src/ChunkCoordinates;"))
	private void attemptBugFix(World newWorld, ChunkCoordinates respawnLocation, CallbackInfoReturnable<Integer> cir) {
		respawnLocation.posX = spawnChunk.posX;
		respawnLocation.posY = spawnChunk.posY;
		respawnLocation.posZ = spawnChunk.posZ;
	}
	@Inject(method = "isValidOngoingAttackTargetForSquid", at = @At("HEAD"), cancellable = true)
	private void makeSquidNotAttackPlayer(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
}
