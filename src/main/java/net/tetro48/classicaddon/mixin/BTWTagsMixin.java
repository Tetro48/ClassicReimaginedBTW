package net.tetro48.classicaddon.mixin;

import btw.item.BTWItems;
import btw.item.tag.BTWTags;
import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(BTWTags.class)
public abstract class BTWTagsMixin {
	@Unique private static Item[] removeItemFromArray(Item[] items, Item itemToRemove) {
		List<Item> lessItems = new ArrayList<>(Arrays.stream(items).toList());
		lessItems.remove(itemToRemove);
		return lessItems.toArray(new Item[0]);
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(value = "INVOKE", ordinal = 6, target = "Lbtw/item/tag/Tag;of(Lnet/minecraft/src/ResourceLocation;[Lnet/minecraft/src/Item;)Lbtw/item/tag/Tag;"))
	private static Item[] removeRawMysteryMeat(Item[] items) {
		return removeItemFromArray(items, BTWItems.rawMysteryMeat);
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(value = "INVOKE", ordinal = 7, target = "Lbtw/item/tag/Tag;of(Lnet/minecraft/src/ResourceLocation;[Lnet/minecraft/src/Item;)Lbtw/item/tag/Tag;"))
	private static Item[] removeCookedMysteryMeat(Item[] items) {
		return removeItemFromArray(items, BTWItems.cookedMysteryMeat);
	}
	@ModifyArg(method = "<clinit>", index = 1, at = @At(value = "INVOKE", ordinal = 8, target = "Lbtw/item/tag/Tag;of(Lnet/minecraft/src/ResourceLocation;[Lnet/minecraft/src/Item;)Lbtw/item/tag/Tag;"))
	private static Item[] removeMysteryMeatFromHearty(Item[] items) {
		return removeItemFromArray(items, BTWItems.cookedMysteryMeat);
	}
}
