package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.NetherrackBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherrackBlock.class)
public abstract class NetherrackBlockMixin {
    @Inject(method = "getEfficientToolLevel", at = @At("RETURN"), cancellable = true)
    public void makeItMineableByAnyPickaxe(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}
