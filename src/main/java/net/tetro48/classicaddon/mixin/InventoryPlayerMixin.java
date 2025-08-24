package net.tetro48.classicaddon.mixin;

import btw.item.items.HoeItem;
import net.minecraft.src.Block;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryPlayer.class)
public abstract class InventoryPlayerMixin {
	@Shadow public abstract ItemStack getStackInSlot(int par1);

	@Shadow public int currentItem;

	@Inject(method = "canHarvestBlock", at = @At("HEAD"), cancellable = true)
	private void canTheFuckingHoesTillTheGoddamnDirt(World world, Block par1Block, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
		ItemStack var2 = this.getStackInSlot(this.currentItem);
		boolean isHoe = var2 != null && var2.getItem() instanceof HoeItem;
		if (isHoe && par1Block.canConvertBlock(var2, world, i, j, k)) {
			cir.setReturnValue(false);
		}
	}
}
