package net.tetro48.classicaddon.mixin.blocks;

import api.block.blocks.OreBlock;
import net.minecraft.src.BlockOre;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OreBlock.class)
public abstract class OreBlockMixin extends BlockOre {

	public OreBlockMixin(int i) {
		super(i);
	}

	@Inject(method = "getRequiredToolLevelForStrata", at = @At("RETURN"), cancellable = true)
	public void makeItMineableByAnyPickaxe(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
	@Inject(method = "getBlockHardness", at = @At("RETURN"), cancellable = true)
	public void sameBreakingSpeed(World world, int i, int j, int k, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(super.getBlockHardness(world, i, j, k));
	}
}
