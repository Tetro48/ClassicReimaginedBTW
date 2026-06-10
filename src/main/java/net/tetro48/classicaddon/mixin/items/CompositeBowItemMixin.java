package net.tetro48.classicaddon.mixin.items;

import btw.item.items.CompositeBowItem;
import net.minecraft.src.ItemBow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CompositeBowItem.class)
public abstract class CompositeBowItemMixin extends ItemBow {

	public CompositeBowItemMixin(int par1) {
		super(par1);
	}

	// go use an anvil, or infernal enchanter, lol
	@Override
	public int getItemEnchantability() {
		return 0;
	}
}
