package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.DailyGrowthCropsBlock;
import btw.block.blocks.SaplingBlock;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlockMixin extends DailyGrowthCropsBlock {
	protected SaplingBlockMixin(int iBlockID) {
		super(iBlockID);
	}

	@Inject(method = "attemptToGrow", at = @At("HEAD"), remap = false)
	private void alwaysGrow(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
		if (this.getHasGrownToday(world, x, y, z)) {
			this.setHasGrownToday(world, x, y, z, false);
		}
	}
	@Redirect(method = "attemptToGrow", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/WorldInfo;getWorldTime()J"))
	private long middleOfTheDayAlways(WorldInfo instance) {
		return 6000;
	}
}
