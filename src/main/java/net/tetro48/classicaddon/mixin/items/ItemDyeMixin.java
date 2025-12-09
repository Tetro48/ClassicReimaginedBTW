package net.tetro48.classicaddon.mixin.items;

import api.util.color.Color;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemDye.class)
public abstract class ItemDyeMixin {
	@Shadow protected abstract boolean creativeGrow(World world, int x, int y, int z);

	@Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
	private void makeBonemealApplicableOnGrass(ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, CallbackInfoReturnable<Boolean> cir) {
		if (itemStack.getItemDamage() == Color.WHITE.colorID) {
			if (world.getBlockId(i, j, k) == Block.grass.blockID) {
				if (player.capabilities != null && !player.capabilities.isCreativeMode) {
					itemStack.stackSize--;
				}
				if (this.creativeGrow(world, i, j, k)) {
					if (!world.isRemote) {
						world.playAuxSFX(2005, i, j, k, 0);
					}
					cir.setReturnValue(true);
				}
			}
		}
	}
}
