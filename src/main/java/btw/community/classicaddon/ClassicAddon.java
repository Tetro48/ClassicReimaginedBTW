package btw.community.classicaddon;

import btw.AddonHandler;
import btw.BTWAddon;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Minecraft;

import java.util.Map;

public class ClassicAddon extends BTWAddon {
    private static ClassicAddon instance;

    public static int planksHandChopped;
    public static int planksWithStoneAxe;
    public static int planksWithIronAxes;
    public static int planksWithSaw;

    public ClassicAddon() {
        super();
    }

    @Override
    public void handleConfigProperties(Map<String, String> propertyValues) {
        planksHandChopped = Integer.parseInt(propertyValues.get("PlanksFromHand"));
        planksWithStoneAxe = Integer.parseInt(propertyValues.get("PlanksWithStoneAxe"));
        planksWithIronAxes = Integer.parseInt(propertyValues.get("PlanksWithIronAxes"));
        planksWithSaw = Integer.parseInt(propertyValues.get("PlanksWithSaw"));
    }
    @Override
    public void preInitialize() {
        this.registerProperty("PlanksFromHand", "2", "The amount of planks you get from just using logs on a grid");
        this.registerProperty("PlanksWithStoneAxe", "3", "The amount of planks you get with stone axe.");
        this.registerProperty("PlanksWithIronAxes", "4", "The amount of planks you get with iron or better axe.");
        this.registerProperty("PlanksWithSaw", "6", "The amount of planks you get from sawing planks");
    }
    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }
}