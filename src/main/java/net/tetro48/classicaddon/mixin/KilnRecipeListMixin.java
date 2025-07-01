package net.tetro48.classicaddon.mixin;

import btw.crafting.recipe.KilnRecipeList;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(KilnRecipeList.class)
public abstract class KilnRecipeListMixin {

	@ModifyArg(method = "addRecipes", index = 0, at = @At(value = "INVOKE", ordinal = 0, target = "Lbtw/crafting/recipe/RecipeManager;addKilnRecipe(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/Block;B)V"))
	private static ItemStack modifyGoldChunkRecipe(ItemStack outputStack) {
		return new ItemStack(Item.ingotGold);
	}
	@ModifyArg(method = "addRecipes", index = 0, at = @At(value = "INVOKE", ordinal = 1, target = "Lbtw/crafting/recipe/RecipeManager;addKilnRecipe(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/Block;B)V"))
	private static ItemStack modifyGoldChunkBlockRecipe(ItemStack outputStack) {
		return new ItemStack(Block.blockGold);
	}
	@ModifyArg(method = "addRecipes", index = 0, at = @At(value = "INVOKE", ordinal = 2, target = "Lbtw/crafting/recipe/RecipeManager;addKilnRecipe(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/Block;B)V"))
	private static ItemStack modifyGoldOreRecipe(ItemStack outputStack) {
		return new ItemStack(Item.ingotGold);
	}
	@ModifyArg(method = "addRecipes", index = 0, at = @At(value = "INVOKE", ordinal = 3, target = "Lbtw/crafting/recipe/RecipeManager;addKilnRecipe(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/Block;B)V"))
	private static ItemStack modifyIronChunkRecipe(ItemStack outputStack) {
		return new ItemStack(Item.ingotIron);
	}
	@ModifyArg(method = "addRecipes", index = 0, at = @At(value = "INVOKE", ordinal = 4, target = "Lbtw/crafting/recipe/RecipeManager;addKilnRecipe(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/Block;B)V"))
	private static ItemStack modifyIronChunkBlockRecipe(ItemStack outputStack) {
		return new ItemStack(Block.blockIron);
	}
	@ModifyArg(method = "addRecipes", index = 0, at = @At(value = "INVOKE", ordinal = 5, target = "Lbtw/crafting/recipe/RecipeManager;addKilnRecipe(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/Block;B)V"))
	private static ItemStack modifyIronOreRecipe(ItemStack outputStack) {
		return new ItemStack(Item.ingotIron);
	}
	@ModifyArg(method = "addRecipes", index = 2, at = @At(value = "INVOKE", ordinal = 6, target = "Lbtw/crafting/recipe/RecipeManager;addKilnRecipe(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/Block;B)V"))
	private static byte makeCharcoalFromBloodWoodFaster(byte cookTimeMultiplier) {
		return 1;
	}
	@ModifyArg(method = "addRecipes", index = 2, at = @At(value = "INVOKE", ordinal = 7, target = "Lbtw/crafting/recipe/RecipeManager;addKilnRecipe(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/Block;B)V"))
	private static byte makeCharcoalFromLogsFaster(byte cookTimeMultiplier) {
		return 1;
	}
}
