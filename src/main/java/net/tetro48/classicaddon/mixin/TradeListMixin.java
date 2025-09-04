package net.tetro48.classicaddon.mixin;

import btw.entity.mob.villager.trade.TradeList;
import net.minecraft.src.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TradeList.class)
public abstract class TradeListMixin {
	@ModifyArg(method = "addFarmerTrades", index = 0, at = @At(value = "INVOKE", target = "Lbtw/entity/mob/villager/trade/TradeProvider$BuySellItemStep;item(I)Lbtw/entity/mob/villager/trade/TradeProvider$BuySellCountStep;", ordinal = 0), remap = false)
	private static int makeFarmerVillagersBuyNormalDirtInsteadOfLooseDirtYouDamned(int id) {
		return Block.dirt.blockID;
	}
	@ModifyArgs(method = "addBlacksmithTrades", at = @At(value = "INVOKE", ordinal = 2, target = "Lbtw/entity/mob/villager/trade/TradeProvider$BuySellCountStep;itemCount(II)Lbtw/entity/mob/villager/trade/TradeProvider$FinalStep;"), remap = false)
	private static void increaseIronNuggetPrice(Args args) {
		args.set(0, (int)args.get(0) * 2);
		args.set(1, (int)args.get(1) * 2);
	}
	@ModifyArgs(method = "addBlacksmithTrades", at = @At(value = "INVOKE", ordinal = 4, target = "Lbtw/entity/mob/villager/trade/TradeProvider$BuySellCountStep;itemCount(II)Lbtw/entity/mob/villager/trade/TradeProvider$FinalStep;"), remap = false)
	private static void increaseGoldNuggetPrice(Args args) {
		args.set(0, (int)args.get(0) * 2);
		args.set(1, (int)args.get(1) * 2);
	}
	@ModifyArgs(method = "addBlacksmithTrades", at = @At(value = "INVOKE", ordinal = 6, target = "Lbtw/entity/mob/villager/trade/TradeProvider$BuySellCountStep;itemCount(II)Lbtw/entity/mob/villager/trade/TradeProvider$FinalStep;"), remap = false)
	private static void increaseIronIngotPrice(Args args) {
		args.set(0, (int)args.get(0) * 2);
		args.set(1, (int)args.get(1) * 2);
	}
}
