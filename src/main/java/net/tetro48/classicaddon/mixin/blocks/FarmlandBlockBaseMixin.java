package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.FarmlandBlockBase;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FarmlandBlockBase.class)
public abstract class FarmlandBlockBaseMixin extends Block {
	protected FarmlandBlockBaseMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDirtDrop(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.dirt.blockID);
	}
	@Redirect(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/FarmlandBlockBase;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
	public void changeDirtDropOnBadBreak(FarmlandBlockBase instance, World world, int i, int j, int k, int id, int amount, int iDamageDropped, float fChanceOfDrop, World world2, int x, int y, int z, int iMetadata) {
		this.dropItemsIndividually(world, i, j, k, Block.dirt.blockID, 1, iMetadata, fChanceOfDrop);
	}
	@ModifyArg(method = "onFallenUpon", index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;setBlockWithNotify(IIII)Z"))
	private int modifyConversion(int i) {
		return Block.dirt.blockID;
	}

	@ModifyArg(method = "checkForSoilReversion", index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;setBlockWithNotify(IIII)Z"))
	private int modifyReversion(int i) {
		return Block.dirt.blockID;
	}

	@Override
	public float getMovementModifier(World world, int i, int j, int k) {
		return 1F;
	}
}
