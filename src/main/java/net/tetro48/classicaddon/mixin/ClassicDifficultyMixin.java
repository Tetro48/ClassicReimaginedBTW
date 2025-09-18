package net.tetro48.classicaddon.mixin;

import btw.community.classicaddon.ClassicAddon;
import btw.world.util.difficulty.ClassicDifficulty;
import btw.world.util.difficulty.Difficulty;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClassicDifficulty.class)
public abstract class ClassicDifficultyMixin extends Difficulty {
	public ClassicDifficultyMixin(String name) {
		super(name);
	}

	@Override
	public boolean shouldAnimalsKick() {
		return ClassicAddon.hcHoofsiesToggle;
	}

	@Override
	public float getAnimalKickStrengthMultiplier() {
		return ClassicAddon.strongerHoofsiesToggle ? 1f : 0.5f;
	}

	@Override
	public boolean hasHardcoreSpawn() {
		return ClassicAddon.hardcoreSpawnToggle;
	}
}
