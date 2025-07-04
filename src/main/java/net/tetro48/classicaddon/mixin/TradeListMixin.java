package net.tetro48.classicaddon.mixin;

import btw.entity.mob.villager.trade.TradeList;
import net.minecraft.src.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TradeList.class)
public abstract class TradeListMixin {
	@ModifyArg(method = "addFarmerTrades", index = 0, at = @At(value = "INVOKE", target = "Lbtw/entity/mob/villager/trade/TradeProvider$BuySellItemStep;item(I)Lbtw/entity/mob/villager/trade/TradeProvider$BuySellCountStep;", ordinal = 0), remap = false)
	private static int makeFarmerVillagersBuyNormalDirtInsteadOfLooseDirtYouDamned(int id) {
		return Block.dirt.blockID;
	}
}
