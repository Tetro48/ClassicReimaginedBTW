package net.tetro48.classicaddon.mixin;

import btw.crafting.recipe.SmeltingRecipeList;
import btw.item.BTWItems;
import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmeltingRecipeList.class)
public class SmeltingRecipeListMixin {

    @Inject(method = "addSmeltingRecipes", at = @At("TAIL"), remap = false)
    private static void modifySmeltingRecipes(CallbackInfo ci) {

        FurnaceRecipes.smelting().getSmeltingList().remove(BTWItems.unfiredNetherBrick.itemID);
        FurnaceRecipes.smelting().getSmeltingList().remove(BTWItems.unfiredCrudeBrick.itemID);
        FurnaceRecipes.smelting().getSmeltingList().remove(BTWItems.ironOreChunk.itemID);
        FurnaceRecipes.smelting().getSmeltingList().remove(BTWItems.goldOreChunk.itemID);
        FurnaceRecipes.smelting().addSmelting(BTWItems.unfiredNetherBrick.itemID, new ItemStack(BTWItems.netherBrick), 0.0F);
        FurnaceRecipes.smelting().addSmelting(BTWItems.unfiredCrudeBrick.itemID, new ItemStack(Item.brick), 0.0F);
        FurnaceRecipes.smelting().addSmelting(BTWItems.ironOreChunk.itemID, new ItemStack(Item.ingotIron), 0.0F, 1);
        FurnaceRecipes.smelting().addSmelting(BTWItems.goldOreChunk.itemID, new ItemStack(Item.ingotGold), 0.0F, 1);
    }
}
