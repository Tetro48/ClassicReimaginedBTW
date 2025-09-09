package net.tetro48.classicaddon.mixin.entity;

import btw.item.BTWItems;
import net.minecraft.src.EntityWitch;
import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityWitch.class)
public abstract class EntityWitchMixin {
	@Mutable
	@Shadow @Final private static int[] itemDrops;

	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void makeWitchesDropMoreKinds(CallbackInfo ci) {
		int[] newItemDrops = new int[itemDrops.length + 3];
		System.arraycopy(itemDrops, 0, newItemDrops, 0, itemDrops.length);
		newItemDrops[itemDrops.length] = Item.glowstone.itemID;
		newItemDrops[itemDrops.length + 1] = Item.sugar.itemID;
		newItemDrops[itemDrops.length + 2] = Item.redstone.itemID;
		itemDrops = newItemDrops;
	}
}
