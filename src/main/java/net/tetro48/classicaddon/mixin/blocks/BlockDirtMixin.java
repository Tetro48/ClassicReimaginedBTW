package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.FarmlandBlock;
import btw.block.blocks.FullBlock;
import btw.item.items.HoeItem;
import btw.world.util.BlockPos;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockDirt.class)
public abstract class BlockDirtMixin extends FullBlock {
	protected BlockDirtMixin(int iBlockID, Material material) {
		super(iBlockID, material);
	}

	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDirtDrop(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.dirt.blockID);
	}
	@Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/BlockDirt;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
	public void changeDirtDropOnBadBreak(BlockDirt instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
		this.dropItemsIndividually(world, i, j, k, instance.blockID, 1, iMetadata, fChanceOfDrop);
	}
	@Inject(method = "onNeighborDirtDugWithImproperTool", at = @At("HEAD"), cancellable = true)
	protected void noConvertDirt(World world, int i, int j, int k, int iToFacing, CallbackInfo ci) {
		ci.cancel();
	}
	@Redirect(method = "convertBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;setBlockWithNotify(IIII)Z"))
	private boolean hoeConvertDirtToFarmland(World world, int x, int y, int z, int iBlockID, ItemStack stack) {
		int irrigationLevel = 0;
		Item var9 = stack.getItem();
		if (var9 instanceof HoeItem hoe) {
			if (hoe.canHoeHydrateTilledSoil() && this.doesBlockConvertToHydratedFarmland(world, x, y, z)) {
				irrigationLevel = BTWBlocks.farmland.setFullyHydrated(irrigationLevel);
			}
		}

		world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.farmland.blockID, irrigationLevel);

		return false;
	}

	@Unique
	private boolean doesBlockConvertToHydratedFarmland(World world, int x, int y, int z) {
		for(int i = 2; i <= 5; ++i) {
			BlockPos neighborPos = new BlockPos(x + Facing.offsetsXForSide[i], y, z + Facing.offsetsZForSide[i]);
			if (world.getBlockMaterial(neighborPos.x, neighborPos.y, neighborPos.z) == Material.water) {
				return true;
			}

			Block var8 = Block.blocksList[world.getBlockId(neighborPos.x, neighborPos.y, neighborPos.z)];
			if (var8 instanceof FarmlandBlock farmlandBlock) {
				if (farmlandBlock.isHydrated(world.getBlockMetadata(neighborPos.x, neighborPos.y, neighborPos.z))) {
					return farmlandBlock.hasIrrigatingBlocks(world, x, y, z);
				}
			}
		}

		return false;
	}
}
