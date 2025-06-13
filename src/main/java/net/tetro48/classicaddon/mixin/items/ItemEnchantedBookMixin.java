package net.tetro48.classicaddon.mixin.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemEnchantedBook.class)
public abstract class ItemEnchantedBookMixin extends Item {
	public ItemEnchantedBookMixin(int par1) {
		super(par1);
	}

	@Shadow public abstract NBTTagList func_92110_g(ItemStack par1ItemStack);

	@Environment(EnvType.CLIENT)
	@Inject(method = "addInformation", at = @At("RETURN"))
	private void reintroduceEnchantmentInfo(ItemStack itemStack, EntityPlayer player, List<String> infoList, boolean bAdvancedToolTips, CallbackInfo ci) {
		NBTTagList enchantments = func_92110_g(itemStack);

		for (int var5 = 0; var5 < enchantments.tagCount(); ++var5) {
			NBTTagCompound var6 = (NBTTagCompound)enchantments.tagAt(var5);
			Enchantment enchantment = Enchantment.enchantmentsList[var6.getShort("id")];
			if (enchantment != null) {
				infoList.add(StatCollector.translateToLocal(enchantment.getName()) + " " + StatCollector.translateToLocal("enchantment.level." + var6.getShort("lvl")));
			}
		}
	}
}
