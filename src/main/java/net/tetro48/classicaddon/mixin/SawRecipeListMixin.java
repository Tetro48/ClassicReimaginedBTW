package net.tetro48.classicaddon.mixin;

import btw.community.classicaddon.ClassicAddon;
import btw.crafting.recipe.SawRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SawRecipeList.class)
public class SawRecipeListMixin {

    @ModifyArg(method = "addRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;II)V", ordinal = 0), index = 1)
    private static int modifySawOakPlankOutput(int par2) {
        return ClassicAddon.planksWithSaw;
    }
    @ModifyArg(method = "addRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;II)V", ordinal = 1), index = 1)
    private static int modifySawSprucePlankOutput(int par2) {
        return ClassicAddon.planksWithSaw;
    }
    @ModifyArg(method = "addRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;II)V", ordinal = 2), index = 1)
    private static int modifySawBirchPlankOutput(int par2) {
        return ClassicAddon.planksWithSaw;
    }
    @ModifyArg(method = "addRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;II)V", ordinal = 3), index = 1)
    private static int modifySawJunglePlankOutput(int par2) {
        return ClassicAddon.planksWithSaw;
    }
    @ModifyArg(method = "addRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;II)V", ordinal = 4), index = 1)
    private static int modifySawBloodPlankOutput(int par2) {
        return ClassicAddon.planksWithSaw;
    }
}
