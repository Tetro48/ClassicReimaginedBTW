package net.tetro48.classicaddon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import btw.item.BTWItems;

@Mixin(BTWItems.class)
public class BTWItemsMixin {
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeDonut(int par1) {
        return 2;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 3, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedEgg(int par1) {
        return 4;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 4, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeBoiledPotato(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 1)
    private static int changeRawMutton(int par1) {
        return 2;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 5, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedMutton(int par1) {
        return 6;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 6, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedCarrot(int par1) {
        return 4;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 7, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeTastySandwich(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 8, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeSteakAndPotatoes(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 9, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeHamAndEggs(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 10, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeSteakDinner(int par1) {
        return 10;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 11, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changePorkDinner(int par1) {
        return 10;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 12, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeWolfDinner(int par1) {
        return 10;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 13, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeRawKebab(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 14, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedKebab(int par1) {
        return 10;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/SoupItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeChickenSoup(int par1) {
        return 10;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/SoupItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeChowder(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 2, value = "INVOKE", target = "Lbtw/item/items/SoupItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeHeartyStew(int par1) {
        return 12;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 1)
    private static int changeRawCheval(int par1) {
        return 3;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 2, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 1)
    private static int changeCookedCheval(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 16, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedMushroomOmelet(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 18, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedScrambledEggs(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 20, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeChocolate(int par1) {
        return 4;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 21, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeRawLiver(int par1) {
        return 3;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 22, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeCookedLiver(int par1) {
        return 8;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/CuredFoodItem;<init>(IIFLjava/lang/String;)V"), index = 1)
    private static int changeCuredMeat(int par1) {
        return 5;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 23, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeBurnedMeat(int par1) {
        return 3;
    }
    @ModifyArg(method = "instantiateModItems", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"), index = 1)
    private static int changeCarrot(int par1) {
        return 9;
    }
}
