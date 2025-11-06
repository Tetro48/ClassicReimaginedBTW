package btw.community.classicaddon;

import btw.AddonHandler;
import btw.BTWAddon;
import btw.achievement.AchievementProvider;
import btw.achievement.AchievementTab;
import btw.achievement.event.BTWAchievementEvents;
import btw.block.BTWBlocks;
import btw.client.gui.debug.BTWDebugRegistry;
import btw.client.gui.debug.DebugInfoSection;
import btw.client.gui.debug.DebugRegistryUtils;
import btw.crafting.manager.CauldronCraftingManager;
import btw.crafting.manager.SawCraftingManager;
import btw.crafting.manager.SoulforgeCraftingManager;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.customcrafting.LogChoppingRecipe;
import btw.item.BTWItems;
import btw.item.items.ToolItem;
import btw.item.tag.BTWTags;
import btw.item.tag.Tag;
import btw.item.tag.TagInstance;
import btw.world.util.data.DataEntry;
import btw.world.util.data.DataProvider;
import btw.world.util.difficulty.Difficulties;
import btw.world.util.difficulty.Difficulty;
import btw.world.util.difficulty.DifficultyParam;
import emi.dev.emi.emi.runtime.EmiReloadManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.tetro48.classicaddon.SynchronizedConfigProperty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.*;
import java.util.Locale;
import java.util.function.Consumer;

public class ClassicAddon extends BTWAddon {
	private static ClassicAddon instance;

	public static Tag looseCobblestonesTag;

	public static Tag anyCobblestoneTag;

	public static final AchievementTab CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB = new AchievementTab("classic_reimagined_starter_guide").setIcon(Block.grass);
	public static Achievement<ItemStack> GET_WOOD_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_CRAFTING_TABLE_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_WOODEN_PICKAXE_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_LOOSE_COBBLESTONE_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_FURNACE_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_STONE_PICKAXE_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_STONE_HOE_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_GLASS_BOTTLE_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_STONE_SWORD_ACHIEVEMENT;
	public static Achievement<ItemStack> GET_BREAD_ACHIEVEMENT;

	public static final DataEntry.WorldDataEntry<Integer> VANILLA_DIFFICULTY_LEVEL = DataProvider.getBuilder(Integer.class)
			.name("vanilla_difficulty")
			.defaultSupplier(() -> 2)
			.readNBT(NBTTagCompound::getInteger)
			.writeNBT(NBTTagCompound::setInteger)
			.global()
			.build();

	public static int oldPlanksHandChopped;
	public static int oldPlanksWithStoneAxe;
	public static int oldPlanksWithIronAxes;
	public static int oldPlanksWithSaw;

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
	public static boolean chickenJockeyToggle;
	public static boolean guaranteedSeedDrop;
	public static boolean intentionalHungerRegenOffset;
	public static boolean canBabyAnimalEatLooseFood;
	public static boolean hempSeedDropFromTallGrass;
	public static boolean shouldBedsSetSpawn;
	public static boolean passableLeaves;
	public static boolean vanillaifyBuckets;
	public static boolean yeetTooExpensive;
	public static boolean wickerWeavingToggle;
	public static boolean hardcoreStump;
	public static boolean degranularizeHungerSystem;
	public static boolean modernExhaustionLevels;

	public static boolean isServerRunningThisAddon = false;

	private static Hashtable<String, SynchronizedConfigProperty> synchronizedConfigProperties;
	private static List<String> synchronizedPropertyNames;

	public ClassicAddon() {
		super();
	}

	@Override
	public void serverPlayerConnectionInitialized(NetServerHandler serverHandler, EntityPlayerMP playerMP) {
		super.serverPlayerConnectionInitialized(serverHandler, playerMP);
		serverHandler.sendPacketToPlayer(new Packet3Chat(ChatMessageComponent.createFromTranslationKey("classicAddon.synchronizedConfigsCommandHint")));
		serverHandler.sendPacketToPlayer(getOnJoinPacket());
	}

	public Packet250CustomPayload getOnJoinPacket() {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);
		synchronizedConfigProperties.forEach((propertyName, configProperty) -> {
			try {
				dataStream.writeUTF(propertyName);
				dataStream.writeUTF(configProperty.getInternalValue());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		return new Packet250CustomPayload("classicaddon|onJoin", byteStream.toByteArray());
	}

	@Override
	public void handleConfigProperties(Map<String, String> propertyValues) {
		quickHealToggle = Boolean.parseBoolean(propertyValues.get("QuickHealToggle"));
		quickHealTicks = Integer.parseInt(propertyValues.get("QuickHealTicks"));
		cursedDifficultyMode = Boolean.parseBoolean(propertyValues.get("CursedDifficultyMode"));
		animageddonToggle = Boolean.parseBoolean(propertyValues.get("AnimageddonToggle"));
		gloomToggle = Boolean.parseBoolean(propertyValues.get("GloomToggle"));
		if (!MinecraftServer.getIsServer()) {
			visualNewMoonBrightnessLevel = Integer.parseInt(propertyValues.get("VisualNewMoonBrightnessLevel"));
		}
		chickenJockeyToggle = Boolean.parseBoolean(propertyValues.get("ChickenJockeyToggle"));
		guaranteedSeedDrop = Boolean.parseBoolean(propertyValues.get("GuaranteedSeedDrop"));
		intentionalHungerRegenOffset = Boolean.parseBoolean(propertyValues.get("IntentionalHungerRegenOffset"));
		canBabyAnimalEatLooseFood = Boolean.parseBoolean(propertyValues.get("CanBabyAnimalEatLooseFood"));
		hempSeedDropFromTallGrass = Boolean.parseBoolean(propertyValues.get("HempSeedDropFromTallGrass"));
		shouldBedsSetSpawn = Boolean.parseBoolean(propertyValues.get("ShouldBedsSetSpawn"));
		degranularizeHungerSystem = Boolean.parseBoolean(propertyValues.get("DegranularizeHungerSystem"));
		modernExhaustionLevels = Boolean.parseBoolean(propertyValues.get("ModernExhaustionValues"));
		Difficulties.CLASSIC.modifyParam(DifficultyParam.ShouldLargeAnimalsKick.class, Boolean.parseBoolean(propertyValues.get("HCHoofsiesToggle")));
		Difficulties.CLASSIC.modifyParam(DifficultyParam.AnimalKickStrengthMultiplier.class,
				Boolean.parseBoolean(propertyValues.get("StrongerHoofsies")) ? 1f : 0.5f);
		Difficulties.CLASSIC.modifyParam(DifficultyParam.ShouldHardcoreSpawnRadiusIncreaseWithProgress.class, Boolean.parseBoolean(propertyValues.get("ExpandableHardcoreSpawn")));
		synchronizedConfigProperties.forEach(((propertyName, configProperty) -> {
			configProperty.setInternalValue(propertyValues.get(propertyName));
			configProperty.resetExternalValue();
		}));
	}
	@Override
	public void preInitialize() {
		VANILLA_DIFFICULTY_LEVEL.register();
		synchronizedConfigProperties = new Hashtable<>();
		synchronizedPropertyNames = new ArrayList<>(11);
		this.registerProperty("DegranularizeHungerSystem", "False", "Enabling this makes the granular hunger system act like the vanilla hunger system.");
		this.registerProperty("ModernExhaustionValues", "False", "This is a toggle for the modern exhaustion values, best suited for an addon like BTWG.");
		this.registerProperty("QuickHealToggle", "False", "This is a toggle for vMC 1.9+ regeneration system. False (Off) by default.");
		this.registerProperty("QuickHealTicks", "40", "How quickly the regen occurs. 20 ticks = 1 second. 10 ticks is vanilla, 40 ticks is Tetro48's suggested value.");
		this.registerProperty("CursedDifficultyMode", "False", "Allow changing BTW difficulty, but marking it cursed");
		this.registerProperty("GloomToggle", "False", "This toggles gloom effect. Default: False.");
		this.registerPropertyClientOnly("VisualNewMoonBrightnessLevel", "0", "This is purely a visual setting... \n# 0: Pitch black. 1: A tiny bit of light");
		this.registerProperty("IntentionalHungerRegenOffset", "True", "This shifts the regen stop region to be below 8.6 shanks instead of below 9 shanks.\n# This makes regen feel much more consistent, even if internally, it may not exactly match up. Default: True.");
		this.registerProperty("GuaranteedSeedDrop", "True", "This makes sure that crop seeds will always drop, no matter the growth stage, just like in modern vanilla. Default: True.");
		this.registerProperty("HempSeedDropFromTallGrass", "True", "This toggles the 1% drop chance for hemp seeds from tall grass. Default: True.");
		this.registerProperty("ExpandableHardcoreSpawn", "False", "This toggle controls the Hardcore Spawn expansion based on game progression. Default: False.");
		this.registerProperty("ShouldBedsSetSpawn", "False", "Enabling this allows a bed to set your spawn. This is implemented in a slightly janky way, ala fixed /setspawn. Default: False.");
		this.registerProperty("CanBabyAnimalEatLooseFood", "False",
				" *** ANIMAL CONFIGS ***\n\n# A toggle to re-introduce the bug with baby animal eating off of ground. This only works while Animageddon is turned off. Default: False.");
		this.registerProperty("ChickenJockeyToggle", "False", "This toggles spawning of buggy chicken jockeys. Default: False.");
		this.registerProperty("HCHoofsiesToggle", "False", "This toggles the HC Hoofsies mechanic from BTW. This only affects the Classic+ difficulty. Default: False.");
		this.registerProperty("StrongerHoofsies", "False", "Toggling this on makes kicking animals deal 7 HP. This only affects the Classic+ difficulty. Default: False.");
		this.registerSynchronizedProperty("PassableLeaves", "False",
				string -> passableLeaves = Boolean.parseBoolean(string),
				" *** SYNCHRONIZED PROPERTIES ***\n\n# This toggles the passable leaves functionality. Default: False.");
		this.registerSynchronizedProperty("HardcoreSpawnToggle", "False",
				string -> Difficulties.CLASSIC.modifyParam(DifficultyParam.ShouldPlayersHardcoreSpawn.class, Boolean.parseBoolean(string)),
				"This toggles the HC Spawn mechanic from BTW. This only affects the Classic+ difficulty. Default: False.");
		this.registerSynchronizedProperty("VanillaifyBuckets", "True",
				string -> vanillaifyBuckets = Boolean.parseBoolean(string),
				"This option re-introduces vanilla bucket mechanics. This makes screw pumps useless. Default: True.");
		this.registerSynchronizedProperty("YeetTooExpensive", "True",
				string -> yeetTooExpensive = Boolean.parseBoolean(string), "Removes the Too Expensive! limit if enabled. Default: True.");
		this.registerSynchronizedProperty("PlanksFromHand", "2",
				string -> planksHandChopped = MathHelper.clamp_int(Integer.parseInt(string), 1, 64),
				"The amount of planks you get from just using logs on a grid. Default: 2");
		this.registerSynchronizedProperty("PlanksWithStoneAxe", "3",
				string -> planksWithStoneAxe = MathHelper.clamp_int(Integer.parseInt(string), 1, 64),
				"The amount of planks you get with stone axe. Default: 3");
		this.registerSynchronizedProperty("PlanksWithIronAxes", "4",
				string -> planksWithIronAxes = MathHelper.clamp_int(Integer.parseInt(string), 1, 64),
				"The amount of planks you get with iron or better axe. Default: 4");
		this.registerSynchronizedProperty("PlanksWithSaw", "6",
				string -> planksWithSaw = MathHelper.clamp_int(Integer.parseInt(string), 1, 64),
				"The amount of planks you get from sawing planks. Default: 6");
		this.registerSynchronizedProperty("WickerWeavingToggle", "False",
				string -> wickerWeavingToggle = Boolean.parseBoolean(string),
				" *** Silly Synchronized Configs *** \n\n# Wicker weaving crafting recipe toggle. This applies to all difficulties. Default: False.");
		this.registerSynchronizedProperty("HardcoreStump", "False",
				string -> hardcoreStump = Boolean.parseBoolean(string),
				"Enabling this allows chisels to make work stumps from tree stumps. This applies to all difficulties. Default: False.");
	}

	public void registerSynchronizedProperty(String propertyName, String defaultValue, Consumer<String> callback, String comment) {
		this.registerProperty(propertyName, defaultValue, comment);
		synchronizedConfigProperties.put(propertyName, new SynchronizedConfigProperty(propertyName, defaultValue, callback));
		synchronizedPropertyNames.add(propertyName);
	}

	public static void resetAllSynchronizedPropertyValues() {
		synchronizedConfigProperties.forEach((k, v) -> v.resetExternalValue());
	}

	public void registerPropertyClientOnly(String propertyName, String defaultValue, String comment) {
		if (!MinecraftServer.getIsServer()) {
			this.registerProperty(propertyName, defaultValue, comment);
		}
	}

	public static void sendPacketToAllPlayers(Packet packet) {
		for (Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			if (player instanceof EntityPlayerMP) {
				((EntityPlayerMP) player).playerNetServerHandler.sendPacketToPlayer(packet);
			}
		}
	}
	@Override
	public void initialize() {
		this.initializeTags();
		this.initializeAchievements();
		this.initializeRecipes();
		this.modifyItems();
		this.revealItemsToEMI();
		AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		registerPacketHandler("classicaddon|onJoin", (payload, entityPlayer) -> {
			if (payload.length > 0) {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(payload.data);
				DataInputStream dataStream = new DataInputStream(inputStream);
				try {
					while (dataStream.available() > 0) {
						String propertyName = dataStream.readUTF();
						SynchronizedConfigProperty configProperty = synchronizedConfigProperties.get(propertyName);
						String stringValue = dataStream.readUTF();
						if (configProperty != null) {
							configProperty.setExternalValue(stringValue);
						}
					}
				} catch (Exception ignored) {}
				modifyPlankRecipes();
				// post-load only
				if (isServerRunningThisAddon) {
					EmiReloadManager.reload();
				}
			}
			isServerRunningThisAddon = true;
		});
		this.registerAddonCommand(new CommandBase() {
			@Override
			public String getCommandName() {
				return "classicreimagined";
			}

			@Override
			public String getCommandUsage(ICommandSender iCommandSender) {
				if (iCommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())) {
					return "/classicreimagined configs OR /classicreimagined set <param_name> <value> OR /classicreimagined difficulty [vanilla difficulty level]";
				}
				return "/classicreimagined configs OR /classicreimagined difficulty";
			}

			@Override
			public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
				return true;
			}

			@Override
			public void processCommand(ICommandSender iCommandSender, String[] strings) {
				if (strings.length == 0) {
					throw new WrongUsageException(getCommandUsage(iCommandSender));
				}
				if (strings[0].equals("configs")) {
					iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("classicAddon.synchronizedConfigsI18n"));
					synchronizedPropertyNames.forEach((propertyName) ->
							iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(propertyName + ": " + synchronizedConfigProperties.get(propertyName).getInternalValue())));
				} else if (strings[0].equals("difficulty")) {
					if (strings.length == 1 || !iCommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())) {
						iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText("Mob difficulty level is " + new String[]{"Easy", "Normal", "Hard"}[MinecraftServer.getServer().worldServers[0].getData(VANILLA_DIFFICULTY_LEVEL)-1]));
						return;
					}
					int difficultyLevel = Integer.parseInt(strings[1]);
					if (difficultyLevel <= 0 || difficultyLevel > 3) {
						throw new CommandException("Not in vanilla range, excluding Peaceful.");
					}
					MinecraftServer.getServer().worldServers[0].setData(VANILLA_DIFFICULTY_LEVEL, difficultyLevel);
					sendPacketToAllPlayers(new Packet3Chat(ChatMessageComponent.createFromText("Mob difficulty level is set to " + new String[]{"Easy", "Normal", "Hard"}[difficultyLevel-1])));
				} else if (iCommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())) {
					if (strings[0].equals("set")) {
						SynchronizedConfigProperty configProperty = synchronizedConfigProperties.get(strings[1]);
						if (configProperty == null) {
							throw new CommandException("Incorrect property name!");
						}
						if (strings.length < 3) {
							throw new WrongUsageException("/classicreimagined set " + strings[1] + " <value>");
						}
						iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText("You've set property " + strings[1] + " to: " + strings[2] + ". This is temporary, to have it be permanent, this must be done by the server owner/manager."));
						notifyAdmins(iCommandSender, "Property " + strings[1] + " has been set to: " + strings[2] + ".");
						configProperty.setInternalValue(strings[2]);
						configProperty.resetExternalValue();
						sendPacketToAllPlayers(getOnJoinPacket());
						sendPacketToAllPlayers(new Packet3Chat(ChatMessageComponent.createFromText(strings[1] + " got changed to: " + strings[2])));
					}
				} else {
					throw new WrongUsageException(getCommandUsage(iCommandSender));
				}
			}

			@Override
			public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
				if (par2ArrayOfStr.length <= 1) {
					if (par1ICommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())) {
						return getListOfStringsMatchingLastWord(par2ArrayOfStr, "configs", "difficulty", "set");
					}
					else return getListOfStringsMatchingLastWord(par2ArrayOfStr, "configs", "difficulty");
				}
				else if (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set")) {
					String[] strings = synchronizedPropertyNames.toArray(new String[0]);
					return getListOfStringsMatchingLastWord(par2ArrayOfStr, strings);
				}
				return null;
			}
		});
		SoulforgeCraftingManager.getInstance().addRecipe(new ItemStack(BTWBlocks.dragonVessel),
				new Object[]{"IGGI", "IUUI", "IHHI", "IIII", 'I', BTWItems.soulforgedSteelIngot, 'G', Block.fenceIron, 'U', BTWItems.urn, 'H', new ItemStack(BTWBlocks.aestheticOpaque, 1, 3)});
		CauldronCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.heartyStew, 5), new ItemStack[]{new ItemStack(BTWItems.boiledPotato), new ItemStack(BTWItems.cookedCarrot), new ItemStack(BTWItems.brownMushroom, 3), new ItemStack(BTWItems.flour), new ItemStack(BTWItems.cookedMysteryMeat), new ItemStack(Item.bowlEmpty, 5)});
	}

	@Override
	public void postInitialize() {
		super.postInitialize();
		DebugInfoSection coordinateInfoSection = DebugRegistryUtils.registerSection(loc("coordinates"), DebugRegistryUtils.Side.LEFT);
		coordinateInfoSection.orderSectionWithPriority(BTWDebugRegistry.chunksServerSectionID, 1);
		coordinateInfoSection.addEntry((mc, isExtendedDebug) -> {
			if (!isExtendedDebug && ClassicAddon.isServerRunningThisAddon) {
				boolean isPlayerHoldingCompass = mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().itemID == Item.compass.itemID;
				if (!isPlayerHoldingCompass) {
					return Optional.of("§c" + I18n.getString("classicAddon.holdCompassForCoordinates"));
				}
				String direction;
				String string4;
				float yaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
				int directionID = MathHelper.floor_double(yaw / 90.0d + 0.5d) & 3;
				if (directionID == 0) {
					direction = "+Z";
					string4 = "S";
				} else if (directionID == 1) {
					direction = "-X";
					string4 = "W";
				} else if (directionID == 2) {
					direction = "-Z";
					string4 = "N";
				} else {
					direction = "+X";
					string4 = "E";
				}
				return Optional.of(String.format(java.util.Locale.ROOT, "XYZ: %.3f / %.3f / %.3f", mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posZ) + "\n"
						+ String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, string4, yaw, MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch)));
			}
			return Optional.empty();
		});
	}

	public void initializeAchievements() {
		AchievementProvider.NameStep<ItemStack> builder = AchievementProvider.getBuilder(BTWAchievementEvents.ItemEvent.class);
		GET_WOOD_ACHIEVEMENT = builder.name(loc("mine_wood"))
				.icon(Block.wood)
				.displayLocation(-2, 0)
				.triggerCondition(itemStack -> itemStack.itemID == Block.wood.blockID)
				.build()
				.setNoAnnounce()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_CRAFTING_TABLE_ACHIEVEMENT = builder.name(loc("crafting_table"))
				.icon(Block.workbench)
				.displayLocation(-1, 0)
				.triggerCondition(itemStack -> itemStack.itemID == BTWBlocks.workbench.blockID)
				.parents(GET_WOOD_ACHIEVEMENT)
				.build()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_WOODEN_PICKAXE_ACHIEVEMENT = builder.name(loc("wooden_pickaxe"))
				.icon(Item.pickaxeWood)
				.displayLocation(0, 0)
				.triggerCondition(itemStack -> itemStack.itemID == Item.pickaxeWood.itemID)
				.parents(GET_CRAFTING_TABLE_ACHIEVEMENT)
				.build()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_LOOSE_COBBLESTONE_ACHIEVEMENT = builder.name(loc("loose_cobblestone"))
				.icon(BTWBlocks.looseCobblestone)
				.displayLocation(1, 0)
				.triggerCondition(itemStack -> looseCobblestonesTag.test(itemStack))
				.parents(GET_WOODEN_PICKAXE_ACHIEVEMENT)
				.build()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_STONE_HOE_ACHIEVEMENT = builder.name(loc("stone_hoe"))
				.icon(Item.hoeStone)
				.displayLocation(0, 1)
				.triggerCondition(itemStack -> itemStack.itemID == Item.hoeStone.itemID)
				.parents(GET_LOOSE_COBBLESTONE_ACHIEVEMENT)
				.build()
				.setNoAnnounce()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_FURNACE_ACHIEVEMENT = builder.name(loc("furnace"))
				.icon(Block.furnaceIdle)
				.displayLocation(1, 1)
				.triggerCondition(itemStack -> itemStack.itemID == Block.furnaceIdle.blockID)
				.parents(GET_LOOSE_COBBLESTONE_ACHIEVEMENT)
				.build()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_GLASS_BOTTLE_ACHIEVEMENT = builder.name(loc("glass_bottle"))
				.icon(Item.glassBottle)
				.displayLocation(2, 1)
				.triggerCondition(itemStack -> itemStack.itemID == Item.glassBottle.itemID)
				.parents(GET_FURNACE_ACHIEVEMENT)
				.build()
				.setNoAnnounce()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_BREAD_ACHIEVEMENT = builder.name(loc("bread"))
				.icon(Item.bread)
				.displayLocation(1, 2)
				.triggerCondition(itemStack -> itemStack.itemID == Item.bread.itemID)
				.parents(GET_FURNACE_ACHIEVEMENT, GET_STONE_HOE_ACHIEVEMENT, GET_GLASS_BOTTLE_ACHIEVEMENT)
				.build()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_STONE_PICKAXE_ACHIEVEMENT = builder.name(loc("stone_pickaxe"))
				.icon(Item.pickaxeStone)
				.displayLocation(2, -1)
				.triggerCondition(itemStack -> itemStack.itemID == Item.pickaxeStone.itemID)
				.parents(GET_LOOSE_COBBLESTONE_ACHIEVEMENT)
				.build()
				.setNoAnnounce()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
		GET_STONE_SWORD_ACHIEVEMENT = builder.name(loc("stone_sword"))
				.icon(Item.swordStone)
				.displayLocation(0, -1)
				.triggerCondition(itemStack -> itemStack.itemID == Item.swordStone.itemID)
				.parents(GET_LOOSE_COBBLESTONE_ACHIEVEMENT)
				.build()
				.registerAchievement(CLASSIC_REIMAGINED_STARTER_GUIDE_ACHIEVEMENT_TAB);
	}

	public void initializeTags() {
		looseCobblestonesTag = Tag.of(loc("loose_cobblestones"), new ItemStack(BTWBlocks.looseCobblestone, 1, 0), new ItemStack(BTWBlocks.looseCobblestone, 1, 4), new ItemStack(BTWBlocks.looseCobblestone, 1, 8));
		anyCobblestoneTag = Tag.of(loc("any_cobblestone"), looseCobblestonesTag, BTWTags.cobblestones);
	}

	public void initializeRecipes() {
		RecipeManager.addRecipe(new ItemStack(Item.swordStone, 1),
				new Object[]{
						"#",
						"#",
						"/", '#', TagInstance.of(ClassicAddon.anyCobblestoneTag), '/', Item.stick});

		RecipeManager.addRecipe(new ItemStack(Item.shovelStone, 1),
				new Object[]{
						"#",
						"/",
						"/", '#', TagInstance.of(ClassicAddon.anyCobblestoneTag), '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(Item.hoeStone, 1),
				new Object[]{
						"#/",
						" /",
						" /", '#', TagInstance.of(ClassicAddon.anyCobblestoneTag), '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(Item.axeStone, 1),
				new Object[]{
						"# ",
						"#/",
						" /", '#', TagInstance.of(ClassicAddon.anyCobblestoneTag), '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(Item.pickaxeStone, 1),
				new Object[]{
						"###",
						" / ",
						" / ", '#', TagInstance.of(ClassicAddon.anyCobblestoneTag), '/', Item.stick});
		RecipeManager.addRecipe(new ItemStack(Block.furnaceIdle, 1),
				new Object[]{
						"###",
						"# #",
						"###", '#', ClassicAddon.anyCobblestoneTag});
		FurnaceRecipes.smelting().addSmelting(Block.sand.blockID, new ItemStack(Block.glass), 0f, 2);
	}

	public void revealItemsToEMI() {
		Item.axeWood.classicReimagined$revealToEMI();
		Item.hoeWood.classicReimagined$revealToEMI();
		Item.swordWood.classicReimagined$revealToEMI();
		Item.pickaxeWood.classicReimagined$revealToEMI();
		Item.shovelWood.classicReimagined$revealToEMI();
		Item.hoeStone.classicReimagined$revealToEMI();
	}

	private static int getMatchingRecipeIndex(List recipes, IRecipe recipe) {
		for(int iIndex = 0; iIndex < recipes.size(); ++iIndex) {
			IRecipe tempRecipe = (IRecipe)recipes.get(iIndex);
			if (tempRecipe.matches(recipe)) {
				return iIndex;
			}
		}

		return -1;
	}
	private static int findChoppingRecipeWithOnlyOutputs(List recipes, ItemStack output, ItemStack lowQualityOutput) {
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i) instanceof LogChoppingRecipe choppingRecipe) {
				if (output.matches(choppingRecipe.getRecipeOutput(), true) &&
						lowQualityOutput.matches(choppingRecipe.getRecipeOutputLowQuality(), true)) {
					return i;
				}
			}
		}
		return -1;
	}

	private static void modifyRecipeStackOutput(List recipes, ItemStack[] inputs, ItemStack output, int newOutputAmount) {
		ShapelessRecipes tempRecipe = CraftingManager.getInstance().createShapelessRecipe(output, inputs);
		int index = getMatchingRecipeIndex(recipes, tempRecipe);
		if (index == -1) return;
		IRecipe recipe = (IRecipe) recipes.get(index);
		recipe.getRecipeOutput().stackSize = newOutputAmount;
	}

	private static void modifyLogChoppingRecipeStackOutput(List recipes, ItemStack output, ItemStack lowQualityOutput, int newOutputAmount, int newLowQualityOutputAmount) {
		int index = findChoppingRecipeWithOnlyOutputs(recipes, output, lowQualityOutput);
		if (index == -1) return;
		LogChoppingRecipe recipe = (LogChoppingRecipe) recipes.get(index);
		recipe.getRecipeOutput().stackSize = newOutputAmount;
		recipe.getRecipeOutputLowQuality().stackSize = newLowQualityOutputAmount;
	}
	private static void modifyLogSawRecipeStackOutput(Block block, int metadata, ItemStack itemStack, int newOutputAmount) {
		ItemStack[] outputs = SawCraftingManager.instance.getRecipe(block, metadata).getOutput();
		ItemStack desiredStack = null;
		for (ItemStack output : outputs) {
			if (itemStack.itemID == output.itemID && itemStack.getItemDamage() == output.getItemDamage()) {
				desiredStack = output;
				break;
			}
		}
		if (desiredStack != null) {
			desiredStack.stackSize = newOutputAmount;
		}
	}

	public static void modifyPlankRecipes() {
		List recipes = CraftingManager.getInstance().getRecipes();
		for(int i = 0; i < 4; ++i) {
			modifyRecipeStackOutput(recipes, new ItemStack[]{new ItemStack(Block.wood, 1, i)},
					new ItemStack(Block.planks, ClassicAddon.oldPlanksHandChopped, i), ClassicAddon.planksHandChopped);
			modifyLogChoppingRecipeStackOutput(recipes, new ItemStack(Block.planks, ClassicAddon.oldPlanksWithIronAxes, i),
					new ItemStack(Block.planks, ClassicAddon.oldPlanksWithStoneAxe, i), ClassicAddon.planksWithIronAxes, ClassicAddon.planksWithStoneAxe);
		}

		modifyRecipeStackOutput(recipes, new ItemStack[]{new ItemStack(BTWBlocks.bloodWoodLog)},
				new ItemStack(Block.planks, ClassicAddon.oldPlanksHandChopped, 4), ClassicAddon.planksHandChopped);
		modifyLogChoppingRecipeStackOutput(recipes, new ItemStack(Block.planks, ClassicAddon.oldPlanksWithIronAxes, 4),
				new ItemStack(Block.planks, ClassicAddon.oldPlanksWithStoneAxe, 4), ClassicAddon.planksWithIronAxes, ClassicAddon.planksWithStoneAxe);
		modifyLogSawRecipeStackOutput(Block.wood, 0, new ItemStack(Block.planks, ClassicAddon.oldPlanksWithSaw, 0), ClassicAddon.planksWithSaw);
		modifyLogSawRecipeStackOutput(Block.wood, 1, new ItemStack(Block.planks, ClassicAddon.oldPlanksWithSaw, 1), ClassicAddon.planksWithSaw);
		modifyLogSawRecipeStackOutput(Block.wood, 2, new ItemStack(Block.planks, ClassicAddon.oldPlanksWithSaw, 2), ClassicAddon.planksWithSaw);
		modifyLogSawRecipeStackOutput(Block.wood, 3, new ItemStack(Block.planks, ClassicAddon.oldPlanksWithSaw, 3), ClassicAddon.planksWithSaw);
		modifyLogSawRecipeStackOutput(BTWBlocks.bloodWoodLog, 32767, new ItemStack(Block.planks, ClassicAddon.oldPlanksWithSaw, 4), ClassicAddon.planksWithSaw);
		ClassicAddon.oldPlanksHandChopped = ClassicAddon.planksHandChopped;
		ClassicAddon.oldPlanksWithStoneAxe = ClassicAddon.planksWithStoneAxe;
		ClassicAddon.oldPlanksWithIronAxes = ClassicAddon.planksWithIronAxes;
		ClassicAddon.oldPlanksWithSaw = ClassicAddon.planksWithSaw;
	}

	private static ResourceLocation loc(String id) {
		return new ResourceLocation("classicaddon", id);
	}

	@Override
	public void initializeDifficultyCommon(Difficulty difficulty) {
		super.initializeDifficultyCommon(difficulty);
		float inverseStoneToolMultiplier = 1F / difficulty.getParamValue(DifficultyParam.StoneToolSpeedMultiplier.class);
		((ToolItem) Item.axeStone).addCustomEfficiencyMultiplier(inverseStoneToolMultiplier);
		((ToolItem) Item.pickaxeStone).addCustomEfficiencyMultiplier(inverseStoneToolMultiplier);
		((ToolItem) Item.shovelStone).addCustomEfficiencyMultiplier(inverseStoneToolMultiplier);
		((ToolItem) Item.hoeStone).addCustomEfficiencyMultiplier(inverseStoneToolMultiplier);
		((ToolItem) BTWItems.sharpStone).addCustomEfficiencyMultiplier(inverseStoneToolMultiplier);
	}

	public void modifyItems() {
		BTWItems.unbakedCake.setMaxStackSize(64);
		BTWItems.unbakedCookies.setMaxStackSize(64);
		BTWItems.unbakedPumpkinPie.setMaxStackSize(64);
		Item.cake.setMaxStackSize(64);
	}
}