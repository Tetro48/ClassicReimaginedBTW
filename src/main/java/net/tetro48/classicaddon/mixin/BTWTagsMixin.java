package net.tetro48.classicaddon.mixin;

import btw.item.BTWItems;
import btw.item.tag.BTWTags;
import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BTWTags.class)
public abstract class BTWTagsMixin {
	@ModifyArg(method = "<clinit>", index = 1, at = @At(value = "INVOKE", ordinal = 6, target = "Lbtw/item/tag/Tag;of(Lnet/minecraft/src/ResourceLocation;[Lnet/minecraft/src/Item;)Lbtw/item/tag/Tag;"))
	private static Item[] removeMysteryMeat(Item[] items) {
		return new Item[]{Item.beefCooked, Item.porkCooked, BTWItems.cookedMutton, BTWItems.cookedCheval, BTWItems.cookedWolfChop};
	}
}
