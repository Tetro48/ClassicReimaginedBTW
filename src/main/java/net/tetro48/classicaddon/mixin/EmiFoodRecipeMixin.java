package net.tetro48.classicaddon.mixin;

import emi.dev.emi.emi.api.widget.WidgetHolder;
import emi.dev.emi.emi.recipe.btw.EmiFoodRecipe;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EmiFoodRecipe.class)
public abstract class EmiFoodRecipeMixin {
	@Shadow private ResourceLocation TEXTURE;

	@Shadow @Final private float saturationModifier;

	@Shadow @Final private int hunger;

	@Inject(method = "addWidgets", remap = false, at = @At(value = "INVOKE", target = "Lemi/dev/emi/emi/api/widget/WidgetHolder;addSlot(Lemi/dev/emi/emi/api/stack/EmiIngredient;II)Lemi/dev/emi/emi/api/widget/SlotWidget;"))
	private void addSaturationThing(WidgetHolder widgets, CallbackInfo ci) {
		int iSaturationPips = MathHelper.ceiling_float_int(hunger * saturationModifier * 1.3333333334f);
		int iSaturationBars = iSaturationPips/8;
		int iPartialPips = iSaturationPips % 8;
		if (iPartialPips != 0) {
			widgets.addTexture(this.TEXTURE, 10 * iSaturationBars + 25, 5, 9, 9, 16, 27);
			widgets.addTexture(this.TEXTURE, 10 * iSaturationBars + 25 + 8 - iPartialPips, 5, 1 + iPartialPips, 9, 33 - iPartialPips, 27);
			if (hunger / 6 > iSaturationBars) {
				widgets.addTexture(this.TEXTURE, 10 * iSaturationBars + 25, 5, 9, 9, 52, 27);
			}
		}
		for (int i = 0; i < iSaturationBars; i++) {

			widgets.addTexture(TEXTURE, 10 * i + 25, 5, 9, 9, 25, 27);
			if (hunger / 6 > i) {
				widgets.addTexture(this.TEXTURE, 10 * i + 25, 5, 9, 9, 52, 27);
			}
		}
		if (this.hunger % 6 != 0) {
			int offset = 9 - (this.hunger % 6 + 3);
			int haunchUCoord = 62 + offset;
			int haunchXCoord = 10 * (this.hunger / 6) + 25;
			if (this.hunger % 6 == 5) {
				++offset;
				haunchUCoord = 52 + offset;
				--haunchXCoord;
			}
			widgets.addTexture(this.TEXTURE, haunchXCoord + offset + 1, 5, 9 - offset, 9, haunchUCoord, 27);
		}
	}
}
