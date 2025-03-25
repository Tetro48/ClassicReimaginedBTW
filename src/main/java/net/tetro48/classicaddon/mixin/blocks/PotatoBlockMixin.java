package net.tetro48.classicaddon.mixin.blocks;

import btw.block.blocks.PotatoBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PotatoBlock.class)
public abstract class PotatoBlockMixin {
    @ModifyConstant(method = "dropBlockAsItemWithChance", constant = @Constant(intValue = 3))
    public int increasePotatoChance(int original) {
        return 2;
    }
}
