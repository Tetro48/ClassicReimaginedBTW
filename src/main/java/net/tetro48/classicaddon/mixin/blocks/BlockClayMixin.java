package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.BlockClay;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockClay.class)
public abstract class BlockClayMixin {
    @Shadow public abstract void dropBlockAsItemWithChance(World world, int i, int j, int k, int iMetaData, float fChance, int iFortuneModifier);

    @Inject(method = "quantityDroppedWithBonus", at = @At("RETURN"), cancellable = true)
    public void increaseClayQuantity(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() * 4);
    }
    @ModifyArg(method = "dropBlockAsItemWithChance", index = 5, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/BlockClay;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"))
    public int decreaseDirtPiles(int amount) {
        return 2;
    }
    @Inject(method = "dropComponentItemsOnBadBreak", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/src/BlockClay;dropItemsIndividually(Lnet/minecraft/src/World;IIIIIIF)V"), cancellable = true)
    public void alwaysDropClay(World world, int i, int j, int k, int iMetadata, float fChance, CallbackInfoReturnable<Boolean> cir) {
        this.dropBlockAsItemWithChance(world, i, j, k, iMetadata, fChance, 0);
        cir.setReturnValue(true);
        cir.cancel();
    }
}
