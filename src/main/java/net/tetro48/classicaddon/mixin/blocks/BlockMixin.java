package net.tetro48.classicaddon.mixin.blocks;

import btw.block.BTWBlocks;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.data.EmiRemoveFromIndex;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.src.*;
import net.tetro48.classicaddon.InterfaceItemEMI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public abstract class BlockMixin implements InterfaceItemEMI {

	@Override
	public Block classicReimagined$revealToEMI() {
		if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
			for (int i = 0; i < 16; ++i) {
				EmiRemoveFromIndex.removed.remove(EmiStack.of(new ItemStack((Block)(Object)this, 1, i)));
			}
		}
		return (Block)(Object)this;
	}
	@Redirect(method = "getPlayerRelativeBlockHardness", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;isCurrentToolEffectiveOnBlock(Lnet/minecraft/src/Block;III)Z"))
	private boolean changeToCanHarvestBlock(EntityPlayer instance, Block targetBlock, int i, int j, int k){
		return instance.canHarvestBlock(targetBlock, i, j, k);
	}
}
