package net.tetro48.classicaddon.mixin;

import btw.block.BTWBlocks;
import btw.community.classicaddon.ClassicAddon;
import btw.crafting.recipe.CraftingRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingRecipeList.class)
public class CraftingRecipeListMixin {

    @Inject(method = "addLogChoppingRecipes", at = @At("HEAD"), remap = false)
    private static void addNewLogChoppingRecipes(CallbackInfo ci) {
        for(int i = 0; i < 4; ++i) {
            RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(
                    new ItemStack(Block.planks, 2, i),
                    new ItemStack[]{
                            new ItemStack(BTWItems.bark, 1, i),
                            new ItemStack(BTWItems.sawDust, 2, 0)
                    },
                    new ItemStack[]{new ItemStack(Block.wood, 1, i)});
            RecipeManager.addLogChoppingRecipe(new ItemStack(Block.planks, ClassicAddon.planksWithIronAxes, i), new ItemStack[]{
                    new ItemStack(BTWItems.bark, 1, i),
                    new ItemStack(BTWItems.sawDust, 2, 0)},
                    new ItemStack(Block.planks, ClassicAddon.planksWithStoneAxe, i),
                    new ItemStack[]{new ItemStack(BTWItems.bark, 1, i),
                    new ItemStack(BTWItems.sawDust, 2, 0)}, new ItemStack(Block.wood, 1, i));
        }

        RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(
                new ItemStack(Block.planks, 2, 4),
                new ItemStack[]{
                        new ItemStack(BTWItems.bark, 1, 4),
                        new ItemStack(BTWItems.soulDust, 2, 0)
                },
                new ItemStack[]{new ItemStack(BTWBlocks.bloodWoodLog, 1, 0)});
    }

    @Inject(method = "addRecipes", at = @At("TAIL"), remap = false)
    private static void addNewRecipes(CallbackInfo ci){
        RecipeManager.addRecipe(new ItemStack(Block.furnaceIdle, 1),
                new Object[]{
                        "###",
                        "#B#",
                        "###", '#', BTWBlocks.looseCobblestone, 'B', BTWBlocks.idleOven});
        RecipeManager.addRecipe(new ItemStack(Block.chest, 1),
                new Object[]{
                        "###",
                        "# #",
                        "###", '#', Block.planks});
        RecipeManager.addRecipe(new ItemStack(Item.pickaxeWood, 1),
                new Object[]{
                        "###",
                        " / ",
                        " / ", '#', Block.planks, '/', Item.stick});
        RecipeManager.addRecipe(new ItemStack(Item.shovelWood, 1),
                new Object[]{
                        "#",
                        "/",
                        "/", '#', Block.planks, '/', Item.stick});
        RecipeManager.addRecipe(new ItemStack(Item.swordWood, 1),
                new Object[]{
                        "#",
                        "#",
                        "/", '#', Block.planks, '/', Item.stick});
        RecipeManager.addRecipe(new ItemStack(Item.swordStone, 1),
                new Object[]{
                        "#",
                        "#",
                        "/", '#', BTWBlocks.looseCobblestone, '/', Item.stick});
        RecipeManager.addRecipe(new ItemStack(Item.hoeWood, 1),
                new Object[]{
                        "##",
                        " /",
                        " /", '#', Block.planks, '/', Item.stick});
        RecipeManager.addRecipe(new ItemStack(Item.axeWood, 1),
                new Object[]{
                        "#  ",
                        "#/ ",
                        " / ", '#', Block.planks, '/', Item.stick});
        RecipeManager.addRecipe(new ItemStack(BTWBlocks.workbench, 1),
                new Object[]{
                        "##",
                        "##", '#', Block.planks});
        RecipeManager.removeVanillaRecipe(new ItemStack(BTWItems.wickerWeaving, 1, 299), new Object[]{"##", "##", '#', Item.reed});
        RecipeManager.addRecipe(new ItemStack(BTWItems.wickerPane, 1, 299), new Object[]{"##", "##", '#', Item.reed});
    }
}
