package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.CobblestoneBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(CobblestoneBlock.class)
public abstract class CobblestoneBlockMixin extends Block {
	@Shadow public abstract int getStrata(int iMetadata);

	protected CobblestoneBlockMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Redirect(method = "onBlockDestroyedWithImproperTool", at = @At(value = "INVOKE", target = "Lbtw/block/blocks/CobblestoneBlock;dropBlockAsItem(Lnet/minecraft/src/World;IIIII)V"))
	public void noFistingCobble(CobblestoneBlock instance, World world, int i, int j, int k, int metadata, int quantityBonus) {}
	@Inject(method = "idDropped", at = @At("RETURN"), cancellable = true)
	public void makeItDropItself(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(this.blockID);
	}
	@Inject(method = "damageDropped", at = @At("RETURN"), cancellable = true)
	public void dropCorrectStrataCobble(int metadata, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(this.getStrata(metadata));
	}

}
