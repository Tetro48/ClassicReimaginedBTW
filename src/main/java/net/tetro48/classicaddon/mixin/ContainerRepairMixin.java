package net.tetro48.classicaddon.mixin;

import btw.item.BTWItems;
import net.minecraft.src.ContainerRepair;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(ContainerRepair.class)
public abstract class ContainerRepairMixin {
	@Unique
	private static final List<Integer> steelToolsAndArmor = new ArrayList<>(Arrays.asList(
			BTWItems.steelSword.itemID,
			BTWItems.steelPickaxe.itemID,
			BTWItems.steelAxe.itemID,
			BTWItems.steelShovel.itemID,
			BTWItems.mattock.itemID,
			BTWItems.battleaxe.itemID,
			BTWItems.steelHoe.itemID,
			BTWItems.plateHelmet.itemID,
			BTWItems.plateBreastplate.itemID,
			BTWItems.plateLeggings.itemID,
			BTWItems.plateBoots.itemID
	));

	@Redirect(method = "updateRepairOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Item;getIsRepairable(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/ItemStack;)Z"))
	private boolean manageSteelRepair(Item repairItem, ItemStack stack1, ItemStack stack2){
		if(steelToolsAndArmor.contains(repairItem.itemID)){
			return stack2.getItem().itemID == BTWItems.soulforgedSteelIngot.itemID;
		}
		return repairItem.getIsRepairable(stack1,stack2);
	}
}
