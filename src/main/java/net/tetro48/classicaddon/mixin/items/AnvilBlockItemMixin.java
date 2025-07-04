package net.tetro48.classicaddon.mixin.items;

import btw.item.blockitems.AnvilBlockItem;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilBlockItem.class)
public abstract class AnvilBlockItemMixin {
	@Redirect(method = "playPlaceSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;playSoundEffect(DDDLjava/lang/String;FF)V"))
	private void changeSound(World world, double i, double j, double k, String str, float volume, float pitch) {
		world.playSoundEffect(i, j, k, "random.anvil_land", 0.5F, world.rand.nextFloat() * 0.05F + 0.8F);
	}
}
