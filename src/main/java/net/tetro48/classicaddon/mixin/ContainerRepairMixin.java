package net.tetro48.classicaddon.mixin;

import btw.community.classicaddon.ClassicAddon;
import btw.item.BTWItems;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.src.*;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mixin(ContainerRepair.class)
public abstract class ContainerRepairMixin extends Container {
	@Shadow public int maximumCost;
	@Shadow private IInventory inputSlots;
	@Shadow private IInventory outputSlot;
	@Shadow private int stackSizeToBeUsedInRepair;
	@Shadow private String repairedItemName;
	@Shadow @Final private EntityPlayer thePlayer;
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
	@Inject(method = "updateRepairOutput", at = @At("HEAD"), cancellable = true)
	public void overrideUpdateRepairOutput(CallbackInfo ci) {
		ItemStack itemStack = this.inputSlots.getStackInSlot(0);
		this.maximumCost = 1;
		int i = 0;
		long l = 0L;
		int j = 0;
		if (itemStack != null) {
			ItemStack itemStack2 = itemStack.copy();
			ItemStack itemStack3 = this.inputSlots.getStackInSlot(1);
			Map enchantments = EnchantmentHelper.getEnchantments(itemStack2);
			l += itemStack.getRepairCost() + (itemStack3 == null ? 0 : itemStack3.getRepairCost());
			boolean bl = false;
			this.stackSizeToBeUsedInRepair = 0;
			if (itemStack3 != null) {
				bl = itemStack3.itemID == Item.enchantedBook.itemID && Item.enchantedBook.func_92110_g(itemStack3).tagCount() > 0;
				if (itemStack2.isItemStackDamageable() && itemStack2.getItem().getIsRepairable(itemStack, itemStack3)) {
					int k = Math.min(itemStack2.getItemDamageForDisplay(), itemStack2.getMaxDamage() / 4);
					if (k <= 0) {
						this.outputSlot.setInventorySlotContents(0, null);
						this.maximumCost = 0;
						return;
					}

					int m;
					for(m = 0; k > 0 && m < itemStack3.stackSize; ++m) {
						int n = itemStack2.getItemDamageForDisplay() - k;
						itemStack2.setItemDamage(n);
						++i;
						k = Math.min(itemStack2.getItemDamageForDisplay(), itemStack2.getMaxDamage() / 4);
					}

					this.stackSizeToBeUsedInRepair = m;
				} else {
					if (!bl && (itemStack2.itemID != itemStack3.itemID || !itemStack2.isItemStackDamageable())) {
						this.outputSlot.setInventorySlotContents(0, null);
						this.maximumCost = 0;
						return;
					}

					if (itemStack2.isItemStackDamageable() && !bl) {
						int k = itemStack.getMaxDamage() - itemStack.getItemDamageForDisplay();
						int m = itemStack3.getMaxDamage() - itemStack3.getItemDamageForDisplay();
						int n = m + itemStack2.getMaxDamage() * 12 / 100;
						int o = k + n;
						int p = itemStack2.getMaxDamage() - o;
						if (p < 0) {
							p = 0;
						}

						if (p < itemStack2.getItemDamageForDisplay()) {
							itemStack2.setItemDamage(p);
							i += 2;
						}
					}

					Map itemEnchantments = EnchantmentHelper.getEnchantments(itemStack3);
					boolean bl2 = false;
					boolean bl3 = false;

					for(Object enchObj : itemEnchantments.keySet()) {
						Integer enchantmentID = (Integer) enchObj;
						Enchantment enchantment = Enchantment.enchantmentsList[enchantmentID];
						int q = (int) enchantments.getOrDefault(enchantmentID, 0);
						int r = (int) itemEnchantments.get(enchantmentID);
						r = q == r ? r + 1 : Math.max(r, q);
						boolean bl4 = enchantment.canApply(itemStack);
						if (this.thePlayer.capabilities.isCreativeMode || itemStack.itemID == Item.enchantedBook.itemID) {
							bl4 = true;
						}

						for(Object registryEntry2 : enchantments.keySet()) {
							Integer enchantmentID2 = (Integer) registryEntry2;
							if (!enchantmentID2.equals(enchantmentID) && !enchantment.canApplyTogether(Enchantment.enchantmentsList[enchantmentID2])) {
								bl4 = false;
								++i;
							}
						}

						if (!bl4) {
							bl3 = true;
						} else {
							bl2 = true;
							if (r > enchantment.getMaxLevel()) {
								r = enchantment.getMaxLevel();
							}
							enchantments.put(enchantmentID, r);
							int s = 0;
							switch (enchantment.getWeight()) {
								case 1:
									s = 8;
									break;
								case 2:
									s = 4;
								case 3:
								case 4:
								case 6:
								case 7:
								case 8:
								case 9:
								default:
									break;
								case 5:
									s = 2;
									break;
								case 10:
									s = 1;
							}
							if (bl) {
								s = Math.max(1, s / 2);
							}

							i += s * r;
							if (itemStack.stackSize > 1) {
								i = 40;
							}
						}
					}

					if (bl3 && !bl2) {
						this.outputSlot.setInventorySlotContents(0, null);
						this.maximumCost = 0;
						return;
					}
				}
			}

			if (this.repairedItemName != null && !StringUtils.isBlank(this.repairedItemName)) {
				if (!this.repairedItemName.equals(itemStack.getDisplayName())) {
					j = 1;
					i += j;
					itemStack2.setItemName(this.repairedItemName);
				}
			} else if (itemStack.hasDisplayName()) {
				j = 1;
				i += j;
				itemStack2.func_135074_t();
			}

			int t = MathHelper.clamp_int((int) (l + i), 0, 2147483647);
			this.maximumCost = t;
			if (i <= 0) {
				itemStack2 = null;
			}

			if (j == i && j > 0 && this.maximumCost >= 40 && !ClassicAddon.yeetTooExpensive) {
				this.maximumCost = 39;
			}

			if (this.maximumCost >= 40 && !ClassicAddon.yeetTooExpensive && !this.thePlayer.capabilities.isCreativeMode) {
				itemStack2 = null;
			}

			if (itemStack2 != null) {
				int k = itemStack2.getRepairCost();
				if (itemStack3 != null && k < itemStack3.getRepairCost()) {
					k = itemStack3.getRepairCost();
				}

				if (j != i || j == 0) {
					k = k * 2 + 1;
				}

				itemStack2.setRepairCost(k);
				EnchantmentHelper.setEnchantments(enchantments, itemStack2);
			}

			this.outputSlot.setInventorySlotContents(0, itemStack2);
			this.detectAndSendChanges();
		} else {
			this.outputSlot.setInventorySlotContents(0, null);
			this.maximumCost = 0;
		}
		ci.cancel();
	}
}
