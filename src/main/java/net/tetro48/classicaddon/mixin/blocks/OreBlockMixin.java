package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.OreBlock;
import btw.block.blocks.OreBlockStaged;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OreBlock.class)
public abstract class OreBlockMixin {

	@Inject(method = "getRequiredToolLevelForStrata", at = @At("RETURN"), cancellable = true)
	public void makeItMineableByAnyPickaxe(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
}
