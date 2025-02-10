package net.tetro48.classicaddon.mixin;

import btw.item.items.ArmorItem;
import net.minecraft.src.EnumArmorMaterial;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {
     @Mutable @Final @Shadow
     public int armorWeight;
     @Inject(method = "<init>(ILnet/minecraft/src/EnumArmorMaterial;IIID)V", at = @At("TAIL"))
     public void zeroArmorWeight(CallbackInfo ci) {
          armorWeight = 0;
     }
}
