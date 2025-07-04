package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockLeaves.class)
public abstract class BlockLeavesMixin extends Block {
	protected BlockLeavesMixin(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Inject(method = "dropBlockAsItemWithChance", at = @At("TAIL"))
	private void dropAppleOnChance(World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier, CallbackInfo ci) {
		if (!world.isRemote && iMetadata == 0) {
			int appleChance = switch (iFortuneModifier) {
				case 1:
					yield 180;
				case 2:
					yield 160;
				case 3:
					yield 120;
				case 4:
					yield 80;
				default:
					yield 200;
			};
			if (world.rand.nextInt(appleChance) == 0) {
				this.dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.appleRed, 1, 0));
			}
		}
	}
}
