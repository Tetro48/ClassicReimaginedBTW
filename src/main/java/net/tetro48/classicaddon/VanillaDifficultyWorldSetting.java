package net.tetro48.classicaddon;

public interface VanillaDifficultyWorldSetting {
	default int classicReimagined$getVanillaDifficultyID() {return -1;}
	default void classicReimagined$setVanillaDifficultyID(int difficultyID) {}
}
