package net.tetro48.classicaddon.mixin.blocks;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BlockLeavesBase.class)
public abstract class BlockLeavesBaseMixin extends Block {
	@Shadow protected float movementModifier;

	protected BlockLeavesBaseMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(int par1, Material par2Material, boolean par3, CallbackInfo ci) {
		if (!ClassicAddon.passableLeaves) movementModifier = 0.8f;
	}
	@Inject(method = "getBlocksMovement", at = @At("RETURN"), cancellable = true)
	public void getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(ClassicAddon.passableLeaves);
	}
	@Inject(method = "addCollisionBoxesToList", at = @At("RETURN"))
	private void addCollisionIfPassableLeavesIsOff(World world, int x, int y, int z, AxisAlignedBB aabb, List bbList, Entity entity, CallbackInfo ci) {
		AxisAlignedBB var8 = this.getCollisionBoundingBoxFromPool(world, x, y, z);
		if (var8 != null && aabb.intersectsWith(var8) && !ClassicAddon.passableLeaves) {
			bbList.add(var8);
		}
	}
}
