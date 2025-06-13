package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFurnace;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFurnace.class)
public abstract class BlockFurnaceMixin extends Block {

	protected BlockFurnaceMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(int par1, boolean par2, CallbackInfo ci) {
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void changeDropType(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(Block.furnaceIdle.blockID);
	}

	@Inject(method = "quantityDropped", at = @At("RETURN"), cancellable = true)
	public void changeDropQuantity(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(1);
	}
}
