package net.tetro48.classicaddon.mixin.blocks;

import btw.block.util.Flammability;
import net.minecraft.src.Block;
import net.minecraft.src.BlockWorkbench;
import net.minecraft.src.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockWorkbench.class)
public class BlockWorkbenchMixin extends Block {
    protected BlockWorkbenchMixin(int par1, Material par2Material) {
        super(par1, par2Material);
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void addStats(int par1, CallbackInfo ci) {
        this.setAxesEffectiveOn();
        this.setChiselsEffectiveOn();
        this.setBuoyant();
        this.setFireProperties(Flammability.PLANKS);
        this.setStepSound(soundWoodFootstep);
        this.setHardness(1.25F);
        this.setResistance(3.33F);
    }
}
