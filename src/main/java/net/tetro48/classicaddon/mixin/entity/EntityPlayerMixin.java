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
	@ModifyArg(method = "addExhaustionForJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V", ordinal = 0))
	private float makeSprintJumpsExpensiveAgain(float par1) {
		return 0.8f;
	}
	@ModifyArg(method = "addExhaustionForJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V", ordinal = 1))
	private float makeJumpsExpensiveAgain(float par1) {
		return 0.2f;
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
}
