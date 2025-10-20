package net.tetro48.classicaddon.mixin;

import net.minecraft.src.WorldSettings;
import net.tetro48.classicaddon.VanillaDifficultyWorldSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldSettings.class)
public abstract class WorldSettingsMixin implements VanillaDifficultyWorldSetting {
	@Unique private int difficultyID = -1;
	@Override
	public int classicReimagined$getVanillaDifficultyID() {
		return difficultyID;
	}

	@Override
	public void classicReimagined$setVanillaDifficultyID(int difficultyID) {
		this.difficultyID = difficultyID;
	}
}
