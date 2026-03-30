package net.tetro48.classicaddon.mixin;

import btw.entity.mob.villager.TradeList;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TradeList.class)
public abstract class TradeListMixin {
	@ModifyArg(method = "addFarmerTrades", index = 0, at = @At(ordinal = 0, value = "INVOKE", target = "Lapi/entity/mob/villager/TradeProvider$BuySellItemStep;item(I)Lapi/entity/mob/villager/TradeProvider$BuySellCountStep;"), remap = false)
	private static int makeFarmerVillagersBuyNormalDirtInsteadOfLooseDirtYouDamned(int id) {
		return Block.dirt.blockID;
	}
	@ModifyArgs(method = "addFarmerTrades", at = @At(ordinal = 14, value = "INVOKE", target = "Lapi/entity/mob/villager/TradeProvider$BuySellCountStep;itemCount(II)Lapi/entity/mob/villager/TradeProvider$FinalStep;"), remap = false)
	private static void increaseAppleCount(Args args) {
		args.set(0, (int)args.get(0) * 2);
		args.set(1, (int)args.get(1) * 2);
	}
	@ModifyArg(method = "addBlacksmithTrades", at = @At(value = "INVOKE", ordinal = 1, target = "Lapi/entity/mob/villager/TradeProvider$BuySellItemStep;item(I)Lapi/entity/mob/villager/TradeProvider$BuySellCountStep;"), remap = false)
	private static int increaseIronNuggetPriceBy9x(int id) {
		return Item.ingotIron.itemID;
	}
	@ModifyArg(method = "addBlacksmithTrades", at = @At(value = "INVOKE", ordinal = 5, target = "Lapi/entity/mob/villager/TradeProvider$BuySellItemStep;item(I)Lapi/entity/mob/villager/TradeProvider$BuySellCountStep;"), remap = false)
	private static int increaseGoldNuggetPriceBy9x(int id) {
		return Item.ingotGold.itemID;
	}
	@ModifyArg(method = "addBlacksmithTrades", index = 0, at = @At(value = "INVOKE", ordinal = 6, target = "Lapi/entity/mob/villager/TradeProvider$BuySellItemStep;item(I)Lapi/entity/mob/villager/TradeProvider$BuySellCountStep;"), remap = false)
	private static int increaseIronIngotPriceBy9x(int id) {
		return Block.blockIron.blockID;
	}
	@ModifyArgs(method = "addBlacksmithTrades", remap = false, at = @At(value = "INVOKE", target = "Lapi/entity/mob/villager/TradeProvider$SecondaryCostStep;secondaryEmeraldCost(II)Lapi/entity/mob/villager/TradeProvider$FinalStep;"))
	private static void reduceBlacksmithScrollCost(Args args) {
		args.set(0, 32);
		args.set(1, 48);
	}
	@ModifyArgs(method = "addFarmerTrades", remap = false, at = @At(value = "INVOKE", target = "Lapi/entity/mob/villager/TradeProvider$SecondaryCostStep;secondaryEmeraldCost(II)Lapi/entity/mob/villager/TradeProvider$FinalStep;"))
	private static void reduceFarmerScrollCost(Args args) {
		args.set(0, 32);
		args.set(1, 48);
	}
	@ModifyArgs(method = "addLibrarianTrades", remap = false, at = @At(value = "INVOKE", target = "Lapi/entity/mob/villager/TradeProvider$SecondaryCostStep;secondaryEmeraldCost(II)Lapi/entity/mob/villager/TradeProvider$FinalStep;"))
	private static void reduceLibrarianScrollCost(Args args) {
		args.set(0, 32);
		args.set(1, 48);
	}
	@ModifyArgs(method = "addPriestTrades", remap = false, at = @At(value = "INVOKE", ordinal = 16, target = "Lapi/entity/mob/villager/TradeProvider$SecondaryCostStep;secondaryEmeraldCost(II)Lapi/entity/mob/villager/TradeProvider$FinalStep;"))
	private static void reducePriestScrollCost(Args args) {
		args.set(0, 32);
		args.set(1, 48);
	}
	@ModifyArgs(method = "addButcherTrades", remap = false, at = @At(value = "INVOKE", ordinal = 1, target = "Lapi/entity/mob/villager/TradeProvider$SecondaryCostStep;secondaryEmeraldCost(II)Lapi/entity/mob/villager/TradeProvider$FinalStep;"))
	private static void reduceButcherScrollCost(Args args) {
		args.set(0, 32);
		args.set(1, 48);
	}
}
