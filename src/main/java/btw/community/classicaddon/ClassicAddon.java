package btw.community.classicaddon;

import btw.AddonHandler;
import btw.BTWAddon;
import btw.BTWMod;
import btw.item.BTWItems;
import btw.item.items.ToolItem;
import btw.world.util.difficulty.Difficulty;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

import java.util.Map;
import java.util.Objects;

public class ClassicAddon extends BTWAddon {
	private static ClassicAddon instance;

	public static int planksHandChopped;
	public static int planksWithStoneAxe;
	public static int planksWithIronAxes;
	public static int planksWithSaw;
	public static boolean quickHealToggle;
	public static int quickHealTicks;
	public static boolean animageddonToggle;
	public static boolean cursedDifficultyMode;
	public static boolean gloomToggle;
	public static int visualNewMoonBrightnessLevel = 0;

	public static boolean isServerRunningThisAddon = false;

	public ClassicAddon() {
		super();
	}

	@Override
	public void serverPlayerConnectionInitialized(NetServerHandler serverHandler, EntityPlayerMP playerMP) {
		super.serverPlayerConnectionInitialized(serverHandler, playerMP);
		serverHandler.sendPacketToPlayer(new Packet250CustomPayload("classicaddon|onJoin", new byte[0]));
	}

	@Override
	public void handleConfigProperties(Map<String, String> propertyValues) {
		planksHandChopped = Integer.parseInt(propertyValues.get("PlanksFromHand"));
		planksWithStoneAxe = Integer.parseInt(propertyValues.get("PlanksWithStoneAxe"));
		planksWithIronAxes = Integer.parseInt(propertyValues.get("PlanksWithIronAxes"));
		planksWithSaw = Integer.parseInt(propertyValues.get("PlanksWithSaw"));
		quickHealToggle = Boolean.parseBoolean(propertyValues.get("QuickHealToggle"));
		quickHealTicks = Integer.parseInt(propertyValues.get("QuickHealTicks"));
		cursedDifficultyMode = Boolean.parseBoolean(propertyValues.get("CursedDifficultyMode"));
		animageddonToggle = Boolean.parseBoolean(propertyValues.get("AnimageddonToggle"));
		gloomToggle = Boolean.parseBoolean(propertyValues.get("GloomToggle"));
		if (!MinecraftServer.getIsServer()) {
			visualNewMoonBrightnessLevel = Integer.parseInt(propertyValues.get("VisualNewMoonBrightnessLevel"));
		}
	}
	@Override
	public void preInitialize() {
		this.registerProperty("PlanksFromHand", "2", "The amount of planks you get from just using logs on a grid. Default: 2");
		this.registerProperty("PlanksWithStoneAxe", "3", "The amount of planks you get with stone axe. Default: 3");
		this.registerProperty("PlanksWithIronAxes", "4", "The amount of planks you get with iron or better axe. Default: 4");
		this.registerProperty("PlanksWithSaw", "6", "The amount of planks you get from sawing planks. Default: 6");
		this.registerProperty("QuickHealToggle", "False", "This is a toggle for vMC 1.9+ regeneration system. False (Off) by default.");
		this.registerProperty("QuickHealTicks", "40", "How quickly the regen occurs. 20 ticks = 1 second. 10 ticks is vanilla, 40 ticks is Tetro48's suggested value.");
		this.registerProperty("CursedDifficultyMode", "False", "Allow changing BTW difficulty, but marking it cursed");
		this.registerProperty("GloomToggle", "False", "This toggles gloom effect.");
		this.registerPropertyClientOnly("VisualNewMoonBrightnessLevel", "0", "This is purely a visual setting... \n# 0: Pitch black. 1: A tiny bit of light");
		this.registerProperty("AnimageddonToggle", "False", "A toggle for BTW Animageddon. Turning this off will disable animal hunger, makes sheep's wool insta-grow when grazing one grass, wolves need to be fed once to shit.");
	}
	public void registerPropertyClientOnly(String propertyName, String defaultValue, String comment) {
		if (!MinecraftServer.getIsServer()) {
			this.registerProperty(propertyName, defaultValue, comment);
		}
	}
	@Override
	public void initialize() {
		AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		registerPacketHandler("classicaddon|onJoin", (payload, entityPlayer) -> {
			isServerRunningThisAddon = true;
		});
	}

	@Override
	public void initializeDifficultyCommon(Difficulty difficulty) {
		super.initializeDifficultyCommon(difficulty);

		if (difficulty.shouldIncreaseStoneToolSpeed()) {
			if (Objects.equals(BTWMod.instance.getVersionString(), "3.0.0 Beta Snapshot 3a")){
				((ToolItem) Item.axeStone).applyStandardEfficiencyModifiers();
				((ToolItem) Item.pickaxeStone).applyStandardEfficiencyModifiers();
				((ToolItem) Item.shovelStone).applyStandardEfficiencyModifiers();
				((ToolItem) Item.hoeStone).applyStandardEfficiencyModifiers();
				((ToolItem) BTWItems.sharpStone).applyStandardEfficiencyModifiers();
				return;
			}
			((ToolItem) Item.axeStone).efficiencyOnProperMaterial = EnumToolMaterial.STONE.getEfficiencyOnProperMaterial();
			((ToolItem) Item.pickaxeStone).efficiencyOnProperMaterial = EnumToolMaterial.STONE.getEfficiencyOnProperMaterial();
			((ToolItem) Item.shovelStone).efficiencyOnProperMaterial = EnumToolMaterial.STONE.getEfficiencyOnProperMaterial();
			((ToolItem) Item.hoeStone).efficiencyOnProperMaterial = EnumToolMaterial.STONE.getEfficiencyOnProperMaterial();
			((ToolItem) BTWItems.sharpStone).efficiencyOnProperMaterial = EnumToolMaterial.STONE.getEfficiencyOnProperMaterial();
		}
	}
}