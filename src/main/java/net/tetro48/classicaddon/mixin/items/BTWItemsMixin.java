package net.tetro48.classicaddon.mixin.items;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import btw.item.BTWItems;

@Mixin(BTWItems.class)
public abstract class BTWItemsMixin {
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeDonut(int par1) {
        return 2;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 3, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedEgg(int par1) {
        return 4;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 3, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeCookedEggSaturation(float fSaturationModifier) {
        return 0.8f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 4, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeBoiledPotato(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 4, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeBoiledPotatoSaturation(float fSaturationModifier) {
        return 1.2f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 1)
    private static int changeRawMutton(int par1) {
        return 2;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 5, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedMutton(int par1) {
        return 6;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 5, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeCookedMuttonSaturation(float fSaturationModifier) {
        return 1.2f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 6, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedCarrot(int par1) {
        return 4;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 6, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeCookedCarrotSaturation(float fSaturationModifier) {
        return 1.2f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 7, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeTastySandwich(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 7, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeTastySandwichSaturation(float fSaturationModifier) {
        return 1.2f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 8, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeSteakAndPotatoes(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 8, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeSteakAndPotatoesSaturation(float fSaturationModifier) {
        return 1.25f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 9, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeHamAndEggs(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 9, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeHamAndEggsSaturation(float fSaturationModifier) {
        return 1.25f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 10, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeSteakDinner(int iItemID) {
        return 9;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 11, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changePorkDinner(int iItemID) {
        return 9;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 12, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeWolfDinner(int iItemID) {
        return 9;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 10, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeSteakDinnerSaturation(float fSaturationModifier) {
        return 1.5f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 11, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changePorkDinnerSaturation(float fSaturationModifier) {
        return 1.5f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 12, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeWolfDinnerSaturation(float fSaturationModifier) {
        return 1.5f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 13, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeRawKebab(int par1) {
        return 7;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 13, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeRawKebabSaturation(float fSaturationModifier) {
        return 0.8f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 14, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedKebab(int par1) {
        return 9;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 14, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeCookedKebabSaturation(float fSaturationModifier) {
        return 1.25f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/SoupItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeChickenSoup(int par1) {
        return 9;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/SoupItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeChickenSoupSaturation(float fSaturationModifier) {
        return 1.5f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/SoupItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeChowder(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 2, value = "INVOKE", target = "Lbtw/item/items/SoupItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeHeartyStewSaturation(float fSaturationModifier) {
        return 1.75f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 1)
    private static int changeRawCheval(int par1) {
        return 3;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 2, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 1)
    private static int changeCookedCheval(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 2, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 2)
    private static float changeCookedChevalSaturation(float fSaturationModifier) {
        return 1.2f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 16, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedMushroomOmelet(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 18, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedScrambledEggs(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 16, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeCookedMushroomOmeletSaturation(float fSaturationModifier) {
        return 0.75f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 18, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeCookedScrambledEggsSaturation(float fSaturationModifier) {
        return 0.75f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 20, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeChocolate(int par1) {
        return 4;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 20, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeChocolateSaturation(float fSaturationModifier) {
        return 1.5f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 21, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeRawLiver(int par1) {
        return 3;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 22, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedLiver(int par1) {
        return 9;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 21, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeRawLiverSaturation(float fSaturationModifier) {
        return 0.75f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 22, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 2)
    private static float changeCookedLiverSaturation(float fSaturationModifier) {
        return 1.2f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/CuredFoodItem;<init>(IIFLjava/lang/String;)V"), index = 1)
    private static int changeCuredMeat(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/CuredFoodItem;<init>(IIFLjava/lang/String;)V"), index = 2)
    private static float changeCuredMeatSaturation(float fSaturationModifier) {
        return 0.8f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 23, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeBurnedMeat(int par1) {
        return 3;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"), index = 1)
    private static int changeCarrot(int par1) {
        return 9;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"), index = 2)
    private static float changeCarrotSaturation(float fSaturationModifier) {
        return 1.2f;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/HighResolutionFoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeMashedMelon(int par1) {
        return 3;
    }
}
