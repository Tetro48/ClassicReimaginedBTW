package net.tetro48.classicaddon.mixin;

import btw.crafting.recipe.KilnRecipeList;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(KilnRecipeList.class)
public class KilnRecipeListMixin {

    @Redirect(method = "addRecipes", at = @At(value = "NEW", target = "(Lnet/minecraft/src/Item;)Lnet/minecraft/src/ItemStack;", ordinal = 0))
    private static ItemStack modifyGoldChunkRecipe(Item par1Item) {
        return new ItemStack(Item.ingotGold);
    }
    @Redirect(method = "addRecipes", at = @At(value = "NEW", target = "(Lnet/minecraft/src/Item;)Lnet/minecraft/src/ItemStack;", ordinal = 1))
    private static ItemStack modifyGoldChunkBlockRecipe(Item par1Item) {
        return new ItemStack(Block.blockGold);
    }
    @Redirect(method = "addRecipes", at = @At(value = "NEW", target = "(Lnet/minecraft/src/Item;)Lnet/minecraft/src/ItemStack;", ordinal = 3))
    private static ItemStack modifyIronChunkRecipe(Item par1Item) {
        return new ItemStack(Item.ingotIron);
    }
    @Redirect(method = "addRecipes", at = @At(value = "NEW", target = "(Lnet/minecraft/src/Item;)Lnet/minecraft/src/ItemStack;", ordinal = 4))
    private static ItemStack modifyIronChunkBlockRecipe(Item par1Item) {
        return new ItemStack(Block.blockIron);
    }
}
