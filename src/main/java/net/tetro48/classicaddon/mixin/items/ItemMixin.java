package net.tetro48.classicaddon.mixin.items;

import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Item.class)
public abstract class ItemMixin {
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/PickaxeItem;<init>(ILnet/minecraft/src/EnumToolMaterial;I)V"), index = 2)
    private static int changeWoodPickDurability(int par1) {
        return 59;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changeApple(int par1) {
        return 4;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changeBread(int par1) {
        return 5;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 1)
    private static int changePorkRaw(int par1) {
        return 3;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changePorkCooked(int par1) {
        return 8;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 3, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changeFishCooked(int par1) {
        return 5;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 4, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changeCookie(int par1) {
        return 2;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"), index = 1)
    private static int changeBeefRaw(int par1) {
        return 3;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 5, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changeBeefCooked(int par1) {
        return 8;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeChickenRaw(int par1) {
        return 2;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 6, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changeChickenCooked(int par1) {
        return 6;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"), index = 1)
    private static int changeRawPotato(int par1) {
        return 6;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 2, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"), index = 1)
    private static int changeRawCarrot(int par1) {
        return 9;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 8, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changeBakedPotato(int par1) {
        return 5;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 9, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changePoisonousPotato(int par1) {
        return 2;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 10, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changeGoldenCarrot(int par1) {
        return 5;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 11, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"), index = 1)
    private static int changePumpkinPie(int par1) {
        return 8;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/HighResolutionFoodItem;<init>(IIFZLjava/lang/String;)V"), index = 1)
    private static int changeMelonSlice(int par1) {
        return 6;
    }
    @ModifyArg(method = "<clinit>", at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"), index = 1)
    private static int changePumpkinSeeds(int par1) {
        return 2;
    }
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemAppleGold;<init>(IIFZ)V"), index = 1)
    private static int changeGoldenApple(int par1) {
        return 4;
    }
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemAppleGold;<init>(IIFZ)V"), index = 2)
    private static float changeGoldenAppleSaturation(float f) {
        return 0.5f;
    }
}
