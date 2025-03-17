package net.tetro48.classicaddon.mixin;

import btw.block.BTWBlocks;
import btw.crafting.recipe.PistonPackingRecipeList;
import btw.crafting.recipe.RecipeManager;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonPackingRecipeList.class)
public class PistonPackingRecipeListMixin {
    @Inject(method = "addRecipes", at = @At("HEAD"), remap = false)
    private static void addNewRecipes(CallbackInfo ci) {
        RecipeManager.addPistonPackingRecipe(BTWBlocks.aestheticEarth, 6, new ItemStack(Block.dirt, 2));
    }
}
