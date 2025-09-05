package net.tetro48.classicaddon.mixin.items;

import btw.item.items.HoeItem;
import net.minecraft.src.Item;
import net.minecraft.src.PotionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Item.class)
public abstract class ItemMixin {
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/PickaxeItem;<init>(ILnet/minecraft/src/EnumToolMaterial;I)V"))
	private static int changeWoodPickDurability(int par1) {
		return 59;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeApple(int par1) {
		return 4;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changeAppleSaturation(float par3) {
		return 0.6f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeBread(int par1) {
		return 5;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changeBreadSaturation(float par3) {
		return 1.2f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"))
	private static int changePorkRaw(int par1) {
		return 3;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changePorkCooked(int par1) {
		return 8;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 3, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeFishCooked(int par1) {
		return 5;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changePorkCookedSaturation(float par3) {
		return 1.2f;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 3, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changeFishCookedSaturation(float par3) {
		return 1.2f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 4, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeCookie(int par1) {
		return 2;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;Z)V"))
	private static int changeBeefRaw(int par1) {
		return 3;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 5, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeBeefCooked(int par1) {
		return 8;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 5, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changeBeefCookedSaturation(float par3) {
		return 1.2f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/FoodItem;<init>(IIFZLjava/lang/String;)V"))
	private static int changeChickenRaw(int par1) {
		return 2;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 6, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeChickenCooked(int par1) {
		return 6;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 6, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changeChickenCookedSaturation(float par3) {
		return 1.2f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"))
	private static int changeRawPotato(int par1) {
		return 6;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 1, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"))
	private static float changeRawPotatoSaturation(float fSaturationModifier) {
		return 0.6f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 2, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"))
	private static int changeRawCarrot(int par1) {
		return 9;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 2, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"))
	private static float changeRawCarrotSaturation(float fSaturationModifier) {
		return 0.6f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 8, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeBakedPotato(int par1) {
		return 5;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 8, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changeBakedPotatoSaturation(float par3) {
		return 1.2f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 9, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changePoisonousPotato(int par1) {
		return 2;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 10, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changeGoldenCarrot(int par1) {
		return 6;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 10, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changeGoldenCarrotSaturation(float par3) {
		return 1.6f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 11, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static int changePumpkinPie(int par1) {
		return 8;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 11, value = "INVOKE", target = "Lnet/minecraft/src/ItemFood;<init>(IIFZ)V"))
	private static float changePumpkinPieSaturation(float par3) {
		return 0.6f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/HighResolutionFoodItem;<init>(IIFZLjava/lang/String;)V"))
	private static int changeMelonSlice(int par1) {
		return 6;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/HighResolutionFoodItem;<init>(IIFZLjava/lang/String;)V"))
	private static float changeMelonSliceSaturation(float fSaturationModifier) {
		return 0.6f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 0, value = "INVOKE", target = "Lbtw/item/items/SeedFoodItem;<init>(IIFI)V"))
	private static int changePumpkinSeeds(int par1) {
		return 2;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemAppleGold;<init>(IIFZ)V"))
	private static int changeGoldenApple(int par1) {
		return 4;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemAppleGold;<init>(IIFZ)V"))
	private static float changeGoldenAppleSaturation(float f) {
		return 1.6f;
	}
	@Redirect(method = "<clinit>", at = @At(ordinal = 72, value = "INVOKE", target = "Lnet/minecraft/src/Item;setTextureName(Ljava/lang/String;)Lnet/minecraft/src/Item;"))
	private static Item makeSugarABrewingItem(Item instance, String par1Str) {
		return instance.setTextureName(par1Str).setPotionEffect(PotionHelper.sugarEffect);
	}
	@Redirect(method = "<clinit>", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/src/Item;hideFromEMI()Lnet/minecraft/src/Item;"))
	private static Item doNotHideWoodenSwordFromEMI(Item instance) {
		return instance;
	}
	@Redirect(method = "<clinit>", at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/src/Item;hideFromEMI()Lnet/minecraft/src/Item;"))
	private static Item doNotHideWoodenShovelFromEMI(Item instance) {
		return instance;
	}
	@Redirect(method = "<clinit>", at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/src/Item;hideFromEMI()Lnet/minecraft/src/Item;"))
	private static Item doNotHideWoodenPickaxeFromEMI(Item instance) {
		return instance;
	}
	@Redirect(method = "<clinit>", at = @At(ordinal = 3, value = "INVOKE", target = "Lnet/minecraft/src/Item;hideFromEMI()Lnet/minecraft/src/Item;"))
	private static Item doNotHideWoodenAxeFromEMI(Item instance) {
		return instance;
	}
	@Redirect(method = "<clinit>", at = @At(ordinal = 4, value = "INVOKE", target = "Lnet/minecraft/src/Item;hideFromEMI()Lnet/minecraft/src/Item;"))
	private static Item doNotHideWoodenHoeFromEMI(Item instance) {
		return instance;
	}
	@Redirect(method = "<clinit>", at = @At(ordinal = 5, value = "INVOKE", target = "Lnet/minecraft/src/Item;hideFromEMI()Lnet/minecraft/src/Item;"))
	private static Item doNotHideStoneHoeFromEMI(Item instance) {
		if (instance instanceof HoeItem) {
			return instance;
		}
		return instance.hideFromEMI();
	}
}
