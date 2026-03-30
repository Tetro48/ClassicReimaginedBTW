package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.ISidedInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityBrewingStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntityBrewingStand.class)
public abstract class TileEntityBrewingStandMixin extends TileEntity implements ISidedInventory {
	@Shadow public abstract int[] getAccessibleSlotsFromSide(int par1);

	@Override
	public int[] getSlotsForFace(int i) {
		return getAccessibleSlotsFromSide(i);
	}
}
