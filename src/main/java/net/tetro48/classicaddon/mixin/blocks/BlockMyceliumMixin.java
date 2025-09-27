package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockMycelium.class)
public abstract class BlockMyceliumMixin extends Block {
	protected BlockMyceliumMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDirtDrop(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.dirt.blockID);
	}
	@Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/BlockMycelium;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
	public void changeDirtDropOnBadBreak(BlockMycelium instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
		this.dropItemsIndividually(world, i, j, k, Block.dirt.blockID, 1, iMetadata, fChanceOfDrop);
	}
	@Inject(method = "onNeighborDirtDugWithImproperTool", at = @At("HEAD"), cancellable = true)
	protected void noConvertDirtWithImproperTool(World world, int i, int j, int k, int iToFacing, CallbackInfo ci) {
		ci.cancel();
	}
	@ModifyArg(method = "convertBlock", index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;setBlockWithNotify(IIII)Z"))
	private int hoeConvertGrassToVanillaDirt(int i) {
		return Block.dirt.blockID;
	}
}
