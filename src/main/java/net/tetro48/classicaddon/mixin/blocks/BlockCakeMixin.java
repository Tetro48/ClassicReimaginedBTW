package net.tetro48.classicaddon.mixin.blocks;

import net.minecraft.src.BlockCake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BlockCake.class)
public class BlockCakeMixin {
    @ModifyArg(method = "eatCakeSliceLocal", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/FoodStats;addStats(IF)V"))
    public int changeHungerUnits(int original) {
        return 6;
    }
    @ModifyArg(method = "eatCakeSliceLocal", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/FoodStats;addStats(IF)V"))
    public float changeFatMultiplier(float original) {
        return 0.5f;
    }
}
