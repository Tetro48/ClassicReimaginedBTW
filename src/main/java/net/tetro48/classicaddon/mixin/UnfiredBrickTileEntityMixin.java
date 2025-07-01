package net.tetro48.classicaddon.mixin;

import btw.block.tileentity.UnfiredBrickTileEntity;
import net.minecraft.src.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UnfiredBrickTileEntity.class)
public abstract class UnfiredBrickTileEntityMixin extends TileEntity {
	@Shadow private int cookCounter;
	@Shadow private boolean isCooking;

	@Inject(method = "updateCooking", at = @At(value = "FIELD", target = "Lbtw/block/tileentity/UnfiredBrickTileEntity;cookCounter:I", ordinal = 0), remap = false)
	public void modifyCookingCode(CallbackInfo ci){
		if (this.isCooking) {
			cookCounter += 3;
		}
	}
}