package net.tetro48.classicaddon.mixin;

import btw.block.BTWBlocks;
import btw.community.classicaddon.ClassicAddon;
import btw.crafting.recipe.CraftingRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import btw.item.tag.BTWTags;
import btw.item.tag.TagInstance;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingRecipeList.class)
public abstract class CraftingRecipeListMixin {

	@Inject(method = "addLogChoppingRecipes", at = @At("HEAD"), remap = false, cancellable = true)
	private static void changeLogChoppingRecipes(CallbackInfo ci) {
		for(int i = 0; i < 4; ++i) {
			RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(
					new ItemStack(Block.planks, ClassicAddon.planksHandChopped, i),
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
				new ItemStack(Block.planks, ClassicAddon.planksHandChopped, 4),
				new ItemStack[]{
						new ItemStack(BTWItems.bark, 1, 4),
						new ItemStack(BTWItems.soulDust, 2, 0)
				},
				new ItemStack[]{new ItemStack(BTWBlocks.bloodWoodLog, 1, 0)});
		RecipeManager.addLogChoppingRecipe(new ItemStack(Block.planks, ClassicAddon.planksWithIronAxes, 4), new ItemStack[]{
						new ItemStack(BTWItems.bark, 1, 4),
						new ItemStack(BTWItems.soulDust, 2, 0)},
				new ItemStack(Block.planks, ClassicAddon.planksWithStoneAxe, 4),
				new ItemStack[]{new ItemStack(BTWItems.bark, 1, 4),
						new ItemStack(BTWItems.soulDust, 2, 0)}, new ItemStack(BTWBlocks.bloodWoodLog, 1, 0));
		ci.cancel();
	}

	@ModifyArg(method = "addDirtRecipes", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;I)V", ordinal = 4))
	private static Block changePackedEarthToDirtRecipe(Block par1Block) {
		return Block.dirt;
	}
	@ModifyArg(method = "addDirtRecipes", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;I)V", ordinal = 5))
	private static Block changePackedEarthSlabToDirtRecipe(Block par1Block) {
		return Block.dirt;
	}
	@ModifyArg(method = "addDirtRecipes", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;I)V", ordinal = 1))
	private static Block changeDirtSlabRecipe(Block par1Block) {
		return BTWBlocks.dirtSlab;
	}
	@ModifyArg(method = "addDirtRecipes", index = 0, at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;I)V"))
	private static Block changeDirtRecipe(Block par1Block) {
		return Block.dirt;
	}

	@Unique
	private static void addMortarRecipe(ItemStack input, ItemStack output) {
		addMortarRecipe(input, output, true);
	}
	@Unique
	private static void addMortarRecipe(ItemStack input, ItemStack output, boolean canIronChisel) {
		RecipeManager.addShapelessRecipe(output,
				new Object[] {
						input, Item.clay});
		RecipeManager.addShapelessRecipe(output,
				new Object[] {
						input, BTWItems.netherSludge});
		RecipeManager.addShapelessRecipe(output,
				new Object[] {
						input, Item.slimeBall});
		addUnmortarRecipe(output, input, canIronChisel);
	}
	@Unique
	private static void addUnmortarRecipe(ItemStack input, ItemStack output, boolean canIronChisel) {
		RecipeManager.addShapelessRecipe(output,
				new Object[] {
						input, new ItemStack(BTWItems.diamondChisel, 1, 32767)});
		if (canIronChisel) {
			RecipeManager.addShapelessRecipe(output,
					new Object[]{
							input, new ItemStack(BTWItems.ironChisel, 1, 32767)});
		}
	}
	@ModifyArg(method = "addTorchRecipes", index = 1, at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;<init>(Lnet/minecraft/src/Block;I)V"))
	private static int modifyNethercoalTorchAmount(int par2) {
		return 4;
	}
	@ModifyArg(method = "addTorchRecipes", index = 0, at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/src/CraftingManager;createRecipe(Lnet/minecraft/src/ItemStack;[Ljava/lang/Object;)Lnet/minecraft/src/ShapedRecipes;"))
	private static ItemStack modifyCoalTorchAmount(ItemStack par1ItemStack) {
		par1ItemStack.stackSize = 2;
		return par1ItemStack;
	}

	///True Classic's tags can't be used here, go look at ClassicAddon.java in btw.community.classicaddon
	@Inject(method = "addStoneToolRecipes", at = @At("HEAD"), remap = false, cancellable = true)
	private static void redoStoneToolRecipes(CallbackInfo ci){
		ci.cancel();
	}

	@Inject(method = "addRecipes", at = @At("TAIL"), remap = false)
	private static void addNewRecipes(CallbackInfo ci){
		RecipeManager.addRecipe(new ItemStack(Block.enchantmentTable, 1),
				new Object[]{
						" B ",
						"D#D",
						"###", '#', Block.obsidian, 'B', Item.book, 'D', BTWItems.diamondIngot});
		RecipeManager.addRecipe(new ItemStack(Item.brewingStand, 1),
				new Object[]{
						" B ",
						"###", '#', BTWTags.cobblestones, 'B', Item.blazeRod});
		for (int i = 0; i < 3; i++) {
			addMortarRecipe(new ItemStack(BTWBlocks.looseCobblestone, 1, i<<2), new ItemStack(Block.cobblestone, 1, i), i<2);
			addMortarRecipe(new ItemStack(BTWBlocks.looseCobblestoneSlab, 1, i<<2), new ItemStack(BTWBlocks.cobblestoneSlab, 1, i), i<2);
			addMortarRecipe(new ItemStack(BTWBlocks.looseStoneBrick, 1, i<<2), new ItemStack(Block.stoneBrick, 1, i<<2), i<2);
			addMortarRecipe(new ItemStack(BTWBlocks.looseStoneBrickSlab, 1, i<<2), new ItemStack(BTWBlocks.stoneBrickSlab, 1, i), i<2);
		}
		addMortarRecipe(new ItemStack(BTWBlocks.looseCobblestoneStairs, 1), new ItemStack(Block.stairsCobblestone, 1));
		addMortarRecipe(new ItemStack(BTWBlocks.looseCobbledDeepslateStairs, 1), new ItemStack(BTWBlocks.midStrataCobblestoneStairs, 1));
		addMortarRecipe(new ItemStack(BTWBlocks.looseCobbledBlackstoneStairs, 1), new ItemStack(BTWBlocks.deepStrataCobblestoneStairs, 1), false);
		addMortarRecipe(new ItemStack(BTWBlocks.looseStoneBrickStairs, 1), new ItemStack(Block.stairsStoneBrick, 1));
		addMortarRecipe(new ItemStack(BTWBlocks.looseDeepslateBrickStairs, 1), new ItemStack(BTWBlocks.midStrataStoneBrickStairs, 1));
		addMortarRecipe(new ItemStack(BTWBlocks.looseBlackstoneBrickStairs, 1), new ItemStack(BTWBlocks.deepStrataStoneBrickStairs, 1), false);
		addMortarRecipe(new ItemStack(BTWBlocks.looseBrick, 1), new ItemStack(Block.brick, 1));
		addMortarRecipe(new ItemStack(BTWBlocks.looseBrickStairs, 1), new ItemStack(Block.stairsBrick, 1));
		addMortarRecipe(new ItemStack(BTWBlocks.looseBrickSlab, 1), new ItemStack(Block.stoneSingleSlab, 1, 4));
		addMortarRecipe(new ItemStack(BTWBlocks.looseNetherBrick, 1), new ItemStack(Block.netherBrick, 1));
		addMortarRecipe(new ItemStack(BTWBlocks.looseNetherBrickSlab, 1), new ItemStack(Block.stoneSingleSlab, 1, 6));
		addMortarRecipe(new ItemStack(BTWBlocks.looseNetherBrickStairs, 1), new ItemStack(Block.stairsNetherBrick, 1));
		addMortarRecipe(new ItemStack(BTWBlocks.idleLooseOven, 1), new ItemStack(BTWBlocks.idleOven, 1));
		RecipeManager.addShapelessRecipe(new ItemStack(Item.book, 1),
				new Object[] {
						Item.paper, Item.paper, Item.paper, Item.leather});
		RecipeManager.addShapelessRecipe(new ItemStack(Item.book, 1),
				new Object[] {
						Item.paper, Item.paper, Item.paper, BTWItems.cutLeather});
		RecipeManager.addRecipe(new ItemStack(BTWBlocks.chest, 1),
				new Object[]{
						"###",
						"# #",
						"###", '#', BTWTags.planks});
		RecipeManager.addRecipe(new ItemStack(Item.pickaxeWood, 1),
				new Object[]{
						"###",
						" / ",
						" / ", '#', BTWTags.planks, '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(Item.shovelWood, 1),
				new Object[]{
						"#",
						"/",
						"/", '#', BTWTags.planks, '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(Item.swordWood, 1),
				new Object[]{
						"#",
						"#",
						"/", '#', BTWTags.planks, '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(Item.hoeWood, 1),
				new Object[]{
						"#/",
						" /",
						" /", '#', BTWTags.planks, '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(Item.axeWood, 1),
				new Object[]{
						"#  ",
						"#/ ",
						" / ", '#', BTWTags.planks, '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(BTWBlocks.workbench, 1),
				new Object[]{
						"##",
						"##", '#', BTWTags.planks});
		//inefficient manual (vanilla) method
		RecipeManager.addRecipe(new ItemStack(Block.sandStone, 1),
				new Object[]{
						"##",
						"##", '#', Block.sand});
		RecipeManager.removeVanillaRecipe(new ItemStack(BTWItems.wickerWeaving, 1, 299), new Object[]{"##", "##", '#', Item.reed});
		RecipeManager.removeVanillaRecipe(new ItemStack(Block.anvil), new Object[]{"III", " I ", "III", 'I', Item.ingotIron});
		RecipeManager.addRecipe(new ItemStack(Block.anvil), new Object[]{"III", " i ", "iii", 'I', Block.blockIron, 'i', Item.ingotIron});
		RecipeManager.addRecipe(new ItemStack(BTWItems.wickerPane, 1), new Object[]{"##", "##", '#', Item.reed});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWItems.tastySandwich, 2), new Object[]{new ItemStack(Item.bread), new ItemStack(BTWItems.cookedMysteryMeat)});
		if (!FabricLoader.getInstance().isModLoaded("craftableeyes")) {
			RecipeManager.addShapelessRecipe(new ItemStack(Item.eyeOfEnder), new Object[]{new ItemStack(Item.enderPearl), new ItemStack(Item.blazePowder)});
		}
		RecipeManager.addShapelessRecipe(new ItemStack(Item.nameTag), new Object[] {
			new ItemStack(Item.leather),
			new ItemStack(Item.ingotIron)
		});

		RecipeManager.addShapelessRecipe(new ItemStack(Item.nameTag), new Object[] {
			new ItemStack(BTWItems.cutLeather),
			new ItemStack(Item.ingotIron)
		});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWBlocks.planterWithSoil), new Object[]{new ItemStack(BTWBlocks.planter), new ItemStack(Block.dirt)});
	}
}
