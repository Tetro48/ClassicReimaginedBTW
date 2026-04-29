package net.tetro48.classicaddon.mixin.items;

import api.achievement.AchievementEventDispatcher;
import api.world.BlockPos;
import btw.community.classicaddon.ClassicAddon;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.data.EmiRemoveFromIndex;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.src.*;
import net.tetro48.classicaddon.InterfaceItemEMI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements InterfaceItemEMI {

	@Shadow @Final public int itemID;

	@Override
	public Item classicReimagined$revealToEMI() {
		if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
			for (int i = 0; i < 16; ++i) {
				EmiRemoveFromIndex.removed.remove(EmiStack.of(new ItemStack((Item)(Object)this, 1, i)));
			}
		}
		return (Item)(Object)this;
	}

	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 0, value = "INVOKE", target = "Lapi/item/items/PickaxeItem;<init>(ILnet/minecraft/src/EnumToolMaterial;I)V"))
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
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 1, value = "INVOKE", target = "Lapi/item/items/SeedFoodItem;<init>(IIFI)V"))
	private static int changeRawPotato(int par1) {
		return 6;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 1, value = "INVOKE", target = "Lapi/item/items/SeedFoodItem;<init>(IIFI)V"))
	private static float changeRawPotatoSaturation(float fSaturationModifier) {
		return 0.6f;
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 2, value = "INVOKE", target = "Lapi/item/items/SeedFoodItem;<init>(IIFI)V"))
	private static int changeRawCarrot(int par1) {
		return 9;
	}
	@ModifyArg(method = "<clinit>", index = 2, at = @At(ordinal = 2, value = "INVOKE", target = "Lapi/item/items/SeedFoodItem;<init>(IIFI)V"))
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
	@ModifyArg(method = "<clinit>", index = 1, at = @At(ordinal = 0, value = "INVOKE", target = "Lapi/item/items/SeedFoodItem;<init>(IIFI)V"))
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
	@Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
	private void globalItemUseInject(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10, CallbackInfoReturnable<Boolean> cir) {
		if (par1ItemStack.itemID == 2601 && par3World.getBlockId(par4, par5, par6) == 237 && !par3World.isRemote) {par2EntityPlayer.performHurtAnimation();Entity entity = new EntityWolf(par3World);entity.setPosition(par4+0.5, par5, par6 + 0.5);par3World.spawnEntityInWorld(entity);((EntityWolf)entity).setOwner(par2EntityPlayer.username);((EntityWolf)entity).setTamed(true);AchievementEventDispatcher.triggerEventForNearbyPlayers(ClassicAddon.BlockConvertToEntityInvoke.class, par3World, new BlockPos(par4, par5, par6), 32, new ClassicAddon.BlockConvertToEntity(237, par3World.getBlockMetadata(par4, par5, par6), entity));entity = new EntityLightningBolt(par3World, par4, par5 - 16, par6);par3World.spawnEntityInWorld(entity);par2EntityPlayer.inventory.decrStackSize(par2EntityPlayer.inventory.currentItem, 1);par3World.setBlockToAir(par4, par5, par6);cir.setReturnValue(true);}
	}
}
