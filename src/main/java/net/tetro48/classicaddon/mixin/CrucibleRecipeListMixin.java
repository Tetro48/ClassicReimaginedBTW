package net.tetro48.classicaddon.mixin;

import btw.block.BTWBlocks;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.recipe.CrucibleRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static java.util.Map.entry;

@Mixin(CrucibleRecipeList.class)
public abstract class CrucibleRecipeListMixin {
	@Unique
	private static final Map<Item, Double> applicableItemMultipliers = Map.ofEntries(
			entry(BTWItems.ironNugget, 1.5d),
			entry(Item.ingotIron, 1.5d),
			entry(Item.ingotGold, 1.5d),
			entry(Item.goldNugget, 1.5d)
	);
	@Unique
	private static final Map<Item, Item> nuggetToIngotMap = Map.ofEntries(
			entry(BTWItems.ironNugget, Item.ingotIron),
			entry(Item.goldNugget, Item.ingotGold)
	);

	//This order of functions is super important. DO NOT FUCKING MOVE THEM, OR Y'ALL GET LESS NUGGETS.
	@ModifyArg(method = "addRecipes", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Item;I)V"))
	private static Item convertItem(Item par1, int par2) {
		if (par2 % 6 == 0 && nuggetToIngotMap.containsKey(par1)) return nuggetToIngotMap.get(par1);
		return par1;
	}
	@ModifyArg(method = "addRecipes", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Item;I)V"))
	private static int preMultiply(Item par1, int par2) {
		if (!applicableItemMultipliers.containsKey(par1) || par2 == 9) {
			return par2;
		}
		int result = (int) (par2 * applicableItemMultipliers.get(par1));
		if (result % 9 == 0) result /= 9;
		return result;
	}

	@Inject(method = "addRecipes", at = @At("TAIL"), remap = false)
	private static void tweakResults(CallbackInfo ci) {
		//removes plenty of recipes that may result in deviation of 100% return
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(Item.goldNugget, 7), new ItemStack[]{new ItemStack(BTWItems.ocularOfEnder)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(Item.ingotGold, 1), new ItemStack[]{new ItemStack(BTWItems.goldenDung)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack[]{new ItemStack(Item.goldNugget, 1), new ItemStack(BTWItems.ironNugget, 3)}, new ItemStack[]{new ItemStack(Block.railPowered, 2)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack[]{new ItemStack(BTWItems.ironNugget, 1), new ItemStack(BTWItems.steelNugget, 3)}, new ItemStack[]{new ItemStack(BTWBlocks.steelDetectorRail, 1)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(Item.goldNugget, 12), new ItemStack[]{new ItemStack(BTWBlocks.lightningRod)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 12), new ItemStack[]{new ItemStack(BTWBlocks.ironSpike)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 3), new ItemStack[]{new ItemStack(BTWItems.mail)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 19), new ItemStack[]{new ItemStack(Item.helmetChain, 1, 32767)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 31), new ItemStack[]{new ItemStack(Item.plateChain, 1, 32767)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[]{new ItemStack(BTWBlocks.pulley, 1)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 3), new ItemStack[]{new ItemStack(BTWItems.ironChisel, 1, 32767)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(Item.ingotIron, 7), new ItemStack[]{new ItemStack(Block.anvil)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 4), new ItemStack[]{new ItemStack(Item.compass)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(BTWBlocks.woodenDetectorRail)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(Block.railDetector)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(Block.rail, 2)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 7), new ItemStack[]{new ItemStack(Item.bucketEmpty)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 7), new ItemStack[]{new ItemStack(Item.bucketLava)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 7), new ItemStack[]{new ItemStack(Item.bucketWater)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 7), new ItemStack[]{new ItemStack(Item.bucketMilk)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 7), new ItemStack[]{new ItemStack(BTWItems.cementBucket)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.ironNugget, 7), new ItemStack[]{new ItemStack(BTWItems.milkChocolateBucket)});
		CrucibleStokedCraftingManager.getInstance().removeRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[]{new ItemStack(BTWBlocks.chest)});

		//adds tweaked recipes
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.goldNugget, 8), new ItemStack[]{new ItemStack(BTWItems.ocularOfEnder)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.goldNugget, 8), new ItemStack[]{new ItemStack(BTWItems.goldenDung)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.goldNugget, 1), new ItemStack(Item.ingotIron, 2)}, new ItemStack[]{new ItemStack(Block.railPowered, 2)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.ingotIron, 1), new ItemStack(BTWItems.steelNugget, 3)}, new ItemStack[]{new ItemStack(BTWBlocks.steelDetectorRail, 1)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[]{new ItemStack(BTWBlocks.woodenDetectorRail)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[]{new ItemStack(Block.railDetector)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[]{new ItemStack(Block.rail, 8)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.goldNugget, 11), new ItemStack[]{new ItemStack(BTWBlocks.lightningRod)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 11), new ItemStack[]{new ItemStack(BTWBlocks.ironSpike)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 4), new ItemStack[]{new ItemStack(BTWItems.mail)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 20), new ItemStack[]{new ItemStack(Item.helmetChain, 1, 32767)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 32), new ItemStack[]{new ItemStack(Item.plateChain, 1, 32767)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.ingotIron, 2), new ItemStack(Item.goldNugget, 3)}, new ItemStack[]{new ItemStack(BTWBlocks.pulley, 1)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 4), new ItemStack[]{new ItemStack(BTWItems.ironChisel, 1, 32767)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 31), new ItemStack[]{new ItemStack(Block.anvil, 1)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 4), new ItemStack[]{new ItemStack(Item.compass)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[]{new ItemStack(Item.bucketEmpty)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[]{new ItemStack(Item.bucketLava)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[]{new ItemStack(Item.bucketWater)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[]{new ItemStack(Item.bucketMilk)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[]{new ItemStack(BTWItems.cementBucket)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[]{new ItemStack(BTWItems.milkChocolateBucket)});
	}
}
