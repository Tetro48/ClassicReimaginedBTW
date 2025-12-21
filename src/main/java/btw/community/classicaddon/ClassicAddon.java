package btw.community.classicaddon;

import api.BTWAddon;
import api.AddonHandler;
import api.achievement.AchievementEvents;
import api.achievement.AchievementProvider;
import api.achievement.AchievementTab;
import api.client.debug.DebugInfoSection;
import api.client.debug.DebugRegistry;
import api.client.debug.DebugRegistryUtils;
import api.config.AddonConfig;
import api.entity.mob.villager.TradeProvider;
import api.item.items.ToolItem;
import api.item.tag.Tag;
import api.item.tag.TagInstance;
import api.world.data.DataEntry;
import api.world.data.DataProvider;
import api.world.difficulty.Difficulty;
import api.world.difficulty.DifficultyParam;
import btw.block.BTWBlocks;
import btw.crafting.manager.CauldronCraftingManager;
import btw.crafting.manager.SawCraftingManager;
import btw.crafting.manager.SoulforgeCraftingManager;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.customcrafting.LogChoppingRecipe;
import btw.item.BTWItems;
import btw.item.BTWTags;
import btw.world.BTWDifficulties;
import emi.dev.emi.emi.runtime.EmiReloadManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.tetro48.classicaddon.ModifiableConfigProperty;
import net.tetro48.classicaddon.gui.ConfigGUI;

import java.io.*;
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

	private static Hashtable<String, ModifiableConfigProperty<?>> modifiableConfigProperties;
	private static List<String> modifiablePropertyNames;
	public static AddonConfig addonConfig;

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
		modifiableConfigProperties.forEach((propertyName, configProperty) -> {
			try {
				if (configProperty.canSync()) {
					dataStream.writeUTF(propertyName);
					configProperty.writeToDataStream(dataStream);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		return new Packet250CustomPayload("classicaddon|onJoin", byteStream.toByteArray());
	}

	@Override
	public void handleConfigProperties(AddonConfig addonConfig) {
		modifiableConfigProperties.forEach(((propertyName, configProperty) -> {
			configProperty.setInternalValueToAddonConfig();
			configProperty.resetExternalValue();
		}));
	}

	@Override
	public void registerConfigProperties(AddonConfig config) {
		addonConfig = config;
		config.registerCategoryComment("hunger-system", "*** HUNGER SYSTEM CONFIGS ***");
		this.createModifiableProperty(config, "hunger-system.degranularize-hunger-system", false,
				bool -> degranularizeHungerSystem = bool,
				"Enabling this makes the granular hunger system act like the vanilla hunger system.").register();
		config.updatePath("DegranularizeHungerSystem", "hunger-system.degranularize-hunger-system");
		this.createModifiableProperty(config, "hunger-system.modern-exhaustion-values", false,
				bool -> modernExhaustionLevels = bool,
				"This is a toggle for the modern exhaustion values, best suited for an addon like BTWG.").register();
		config.updatePath("ModernExhaustionValues", "hunger-system.modern-exhaustion-values");
		this.createModifiableProperty(config, "hunger-system.quick-heal-toggle", false,
				bool -> quickHealToggle = bool,
				"This is a toggle for vMC 1.9+ regeneration system. False (Off) by default.").register();
		config.updatePath("QuickHealToggle", "hunger-system.quick-heal-toggle");
		this.createModifiableProperty(config, "hunger-system.quick-heal-ticks", 40,
				integer -> quickHealTicks = integer,
				"How quickly the regen occurs. 20 ticks = 1 second. 10 ticks is vanilla, 40 ticks is Tetro48's suggested value.")
				.setMinMax(1, Integer.MAX_VALUE).register();
		config.updatePath("QuickHealTicks", "hunger-system.quick-heal-ticks");
		this.createModifiableProperty(config, "hunger-system.hunger-regen-offset-toggle", true,
						bool -> intentionalHungerRegenOffset = bool,
						"This shifts the regen stop region to be below 8.6 shanks instead of below 9 shanks.", "This makes regen feel much more consistent, even if internally, it may not exactly match up. Default: True.")
				.register();
		config.updatePath("IntentionalHungerRegenOffset", "hunger-system.hunger-regen-offset-toggle");

		config.registerCategoryComment("difficulty", "*** MISC DIFFICULTY CONFIGS ***");
		this.createModifiableProperty(config, "difficulty.cursed-difficulty-mode", false,
				bool -> cursedDifficultyMode = bool,
				"Allow changing BTW difficulty, but marking it cursed").register();
		config.updatePath("CursedDifficultyMode", "difficulty.cursed-difficulty-mode");
		if (!MinecraftServer.getIsServer()) {
			this.createModifiableProperty(config, "client.new-moon-brightness-level", 0,
					intValue -> visualNewMoonBrightnessLevel = intValue,
					"This is purely a visual setting...", "0: Pitch black. 1: A tiny bit of light")
					.setMinMax(0, 1).register();
			config.updatePath("VisualNewMoonBrightnessLevel", "client.new-moon-brightness-level");
		}

		config.registerCategoryComment("world", "*** WORLD CONFIGS ***");
		this.createModifiableProperty(config, "world.gloom-toggle", false,
				bool -> gloomToggle = bool, "This toggles gloom effect. Default: False.")
				.register();
		config.updatePath("GloomToggle", "world.gloom-toggle");
		this.createModifiableProperty(config, "world.guaranteed-seed-drop", true,
				bool -> guaranteedSeedDrop = bool,
				"This makes sure that crop seeds will always drop, no matter the growth stage, just like in modern vanilla. Default: True.")
				.register();
		config.updatePath("GuaranteedSeedDrop", "world.guaranteed-seed-drop");
		this.createModifiableProperty(config, "world.hemp-seed-drop", true,
				bool -> hempSeedDropFromTallGrass = bool,
				"This toggles the 1% drop chance for hemp seeds from tall grass. Default: True.")
				.register();
		config.updatePath("HempSeedDropFromTallGrass", "world.hemp-seed-drop");
		this.createModifiableProperty(config, "world.expandable-hcs", false,
				bool -> BTWDifficulties.CLASSIC.modifyParam(DifficultyParam.ShouldHardcoreSpawnRadiusIncreaseWithProgress.class, bool),
				"This toggle controls the Hardcore Spawn expansion based on game progression. Default: False.")
				.register();
		config.updatePath("ExpandableHardcoreSpawn", "world.expandable-hcs");
		this.createModifiableProperty(config, "world.bed-spawn-point-toggle", false,
				bool -> shouldBedsSetSpawn = bool,
				"Enabling this allows a bed to set your spawn. This is implemented in a slightly janky way, ala fixed /setspawn. Default: False.")
				.register();
		config.updatePath("ShouldBedsSetSpawn", "world.bed-spawn-point-toggle");

		config.registerCategoryComment("mobs", "*** MOB CONFIGS ***");
		this.createModifiableProperty(config, "mobs.animageddon", false,
				bool -> animageddonToggle = bool,
				"A toggle for all of the BTW's Animageddon. Default: False.")
				.register();
		this.createModifiableProperty(config, "mobs.do-baby-animals-eat-loose-food", false,
				bool -> canBabyAnimalEatLooseFood = bool,
				"A toggle to re-introduce baby animal eating food off of ground. This only works while Animageddon is turned off. Default: False.")
				.register();
		config.updatePath("CanBabyAnimalEatLooseFood", "mobs.do-baby-animals-eat-loose-food");
		this.createModifiableProperty(config, "mobs.chicken-jockey", false,
				bool -> chickenJockeyToggle = bool,
				"This toggles spawning of buggy chicken jockeys. Default: False.")
				.register();
		config.updatePath("ChickenJockeyToggle", "mobs.chicken-jockey");
		this.createModifiableProperty(config, "mobs.hoofsies-toggle", false,
				bool -> BTWDifficulties.CLASSIC.modifyParam(DifficultyParam.ShouldLargeAnimalsKick.class, bool),
				"This toggles the HC Hoofsies mechanic from BTW. This only affects the Classic+ difficulty. Default: False.")
				.register();
		config.updatePath("HCHoofsiesToggle", "mobs.hoofsies-toggle");
		this.createModifiableProperty(config, "mobs.hoofsies-strength", 1d,
				floatValue -> BTWDifficulties.CLASSIC.modifyParam(DifficultyParam.AnimalKickStrengthMultiplier.class, (float)(double)floatValue),
				"Strength multiplier of 1 makes kicking animals deal 7 HP. Strength multiplier of 0.5 makes kicking animals deal 3 HP (rounded down). This only affects the Classic+ difficulty. Default: 1.0.")
				.setMinMax(0.1d, 10d).register();
		config.updatePath("StrongerHoofsies", "mobs.hoofsies-strength");

		config.registerCategoryComment("synchronized", "*** SYNCHRONIZED CONFIGS ***");
		this.createSynchronizedProperty(config, "synchronized.world.passable-leaves", false,
				bool -> passableLeaves = bool,
				"This toggles the passable leaves functionality. Default: False.")
				.register();
		config.updatePath("PassableLeaves", "synchronized.world.passable-leaves");
		this.createSynchronizedProperty(config, "synchronized.world.hcs-toggle", false,
				bool -> BTWDifficulties.CLASSIC.modifyParam(DifficultyParam.ShouldPlayersHardcoreSpawn.class, bool),
				"This toggles the HC Spawn mechanic from BTW. This only affects the Classic+ difficulty. Default: False.")
				.register();
		config.updatePath("HardcoreSpawnToggle", "synchronized.world.hcs-toggle");
		this.createSynchronizedProperty(config, "synchronized.world.vanilla-buckets", true,
				bool -> vanillaifyBuckets = bool,
				"This option re-introduces vanilla bucket mechanics. This makes screw pumps useless. Default: True.")
				.register();
		config.updatePath("VanillaifyBuckets", "synchronized.world.vanilla-buckets");
		this.createSynchronizedProperty(config, "synchronized.anvil.yeet-too-expensive", true,
				bool -> yeetTooExpensive = bool, "Removes the Too Expensive! limit if enabled. Default: True.")
				.register();
		config.updatePath("YeetTooExpensive", "synchronized.anvil.yeet-too-expensive");
		this.createSynchronizedProperty(config, "synchronized.crafting.hand-planks", 2,
				integer -> planksHandChopped = MathHelper.clamp_int(integer, 1, 64),
				"The amount of planks you get from just using logs on a grid. Default: 2")
				.setMinMax(1, 64).register();
		config.updatePath("PlanksFromHand", "synchronized.crafting.hand-planks");
		this.createSynchronizedProperty(config, "synchronized.crafting.stone-axe-planks", 3,
				integer -> planksWithStoneAxe = MathHelper.clamp_int(integer, 1, 64),
				"The amount of planks you get with stone axe. Default: 3")
				.setMinMax(1, 64).register();
		config.updatePath("PlanksWithStoneAxe", "synchronized.crafting.stone-axe-planks");
		this.createSynchronizedProperty(config, "synchronized.crafting.iron-axe-planks", 4,
				integer -> planksWithIronAxes = MathHelper.clamp_int(integer, 1, 64),
				"The amount of planks you get with iron or better axe. Default: 4")
				.setMinMax(1, 64).register();
		config.updatePath("PlanksWithIronAxes", "synchronized.crafting.iron-axe-planks");
		this.createSynchronizedProperty(config, "synchronized.crafting.saw-planks", 6,
				integer -> planksWithSaw = MathHelper.clamp_int(integer, 1, 64),
				"The amount of planks you get from sawing logs. Default: 6")
				.setMinMax(1, 64).register();
		config.updatePath("PlanksWithSaw", "synchronized.crafting.saw-planks");

		config.registerCategoryComment("synchronized.silly", "*** Silly Synchronized Configs ***");
		this.createSynchronizedProperty(config, "synchronized.silly.crafting.wicker-weaving", false,
				bool -> wickerWeavingToggle = bool,
				"Wicker weaving crafting recipe toggle. This applies to all difficulties. Default: False.")
				.register();
		config.updatePath("WickerWeavingToggle", "synchronized.silly.crafting.wicker-weaving");
		this.createSynchronizedProperty(config, "synchronized.silly.world.hc-stump", false,
				bool -> hardcoreStump = bool,
				"Enabling this allows chisels to make work stumps from tree stumps. This applies to all difficulties. Default: False.")
				.register();
		config.updatePath("HardcoreStump", "synchronized.silly.world.hc-stump");
	}

	@Override
	public void preInitialize() {
		VANILLA_DIFFICULTY_LEVEL.register();
		modifiableConfigProperties = new Hashtable<>();
		modifiablePropertyNames = new ArrayList<>(11);
		BTWDifficulties.CLASSIC.modifyParam(DifficultyParam.HungerIntensiveActionCostMultiplier.class, 1f);
	}

	public <T> ModifiableConfigProperty<T> createModifiableProperty(AddonConfig config, String propertyName, T defaultValue, Consumer<T> callback, String... comments) {
		return createModifiableProperty(config, propertyName,defaultValue, false, callback, comments);
	}
	public <T> ModifiableConfigProperty<T> createSynchronizedProperty(AddonConfig config, String propertyName, T defaultValue, Consumer<T> callback, String comments) {
		return createModifiableProperty(config, propertyName,defaultValue, true, callback, comments);
	}
	private <T> ModifiableConfigProperty<T> createModifiableProperty(AddonConfig config, String propertyName, T defaultValue, boolean sync, Consumer<T> callback, String... comments) {
		if (!modifiablePropertyNames.contains(propertyName))
		{
			ModifiableConfigProperty<T> configProperty = new ModifiableConfigProperty<T>(config, propertyName, defaultValue, sync, callback, comments);
			modifiableConfigProperties.put(propertyName, configProperty);
			modifiablePropertyNames.add(propertyName);
			return configProperty;
		}
		else return (ModifiableConfigProperty<T>) modifiableConfigProperties.get(propertyName);
	}

	public static void resetAllSynchronizedPropertyValues() {
		modifiableConfigProperties.forEach((k, v) -> v.resetExternalValue());
	}

	public static ModifiableConfigProperty<?>[] getModifiableConfigPropertiesAsArray() {
		ArrayList<ModifiableConfigProperty<?>> configPropertyArrayList = new ArrayList();
		modifiablePropertyNames.forEach((string) -> configPropertyArrayList.add(modifiableConfigProperties.get(string)));
		return configPropertyArrayList.toArray(ModifiableConfigProperty[]::new);
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
		this.initializeTrades();
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
						ModifiableConfigProperty<?> configProperty = modifiableConfigProperties.get(propertyName);
						if (configProperty != null) {
							configProperty.setExternalValueFromDataStream(dataStream);
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
		registerPacketHandler("classicaddon|openConfig", (payload, entityPlayer) -> {
			if (payload.length > 0) {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(payload.data);
				DataInputStream dataStream = new DataInputStream(inputStream);
				try {
					if (!MinecraftServer.getIsServer()) {
						Minecraft.getMinecraft().displayGuiScreen(new ConfigGUI(null, dataStream.readBoolean()));
					}
				} catch (Exception ignored) {}
			}
		});
		this.registerAddonCommand(new CommandBase() {
			@Override
			public String getCommandName() {
				return "classicreimagined";
			}

			@Override
			public String getCommandUsage(ICommandSender iCommandSender) {
				if (iCommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())) {
					return "/classicreimagined configs [open] OR /classicreimagined set <param_name> <value> OR /classicreimagined difficulty [vanilla difficulty level]";
				}
				return "/classicreimagined configs [open] OR /classicreimagined difficulty";
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
					 if (strings.length == 2 && strings[1].equals("open")) {
						ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
						DataOutputStream dataStream = new DataOutputStream(byteStream);
						try {
							dataStream.writeBoolean(iCommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName()));
						} catch (Exception ignored) {}
						getCommandSenderAsPlayer(iCommandSender).playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("classicaddon|openConfig", byteStream.toByteArray()));
						return;
					}
					iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("classicAddon.synchronizedConfigsI18n"));
					modifiablePropertyNames.forEach((propertyName) ->
							iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(propertyName).addText(": " + modifiableConfigProperties.get(propertyName).getInternalValue())));
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
						ModifiableConfigProperty<?> configProperty = modifiableConfigProperties.get(strings[1]);
						if (configProperty == null) {
							throw new CommandException("Incorrect property name!");
						}
						if (strings.length < 3) {
							throw new WrongUsageException("/classicreimagined set " + strings[1] + " <value>");
						}
						notifyAdmins(iCommandSender, "Property " + strings[1] + " has been set to: " + strings[2] + ".");
						configProperty.parseSetInternalValue(strings[2]);
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
				else if (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("configs"))
					return getListOfStringsMatchingLastWord(par2ArrayOfStr, "open");
				else if (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set")) {
					String[] strings = modifiablePropertyNames.toArray(new String[0]);
					return getListOfStringsMatchingLastWord(par2ArrayOfStr, strings);
				}
				return null;
			}
		});
		SoulforgeCraftingManager.getInstance().addRecipe(new ItemStack(BTWBlocks.dragonVessel),
				new Object[]{
						"IGGI",
						"IUUI",
						"IHHI",
						"IIII",
						'I', BTWItems.soulforgedSteelIngot,
						'G', Block.fenceIron,
						'U', BTWItems.urn,
						'H', new ItemStack(BTWBlocks.aestheticOpaque, 1, 3)}
		);
		CauldronCraftingManager.getInstance().removeRecipe(new ItemStack(BTWItems.heartyStew, 5), new ItemStack[]{new ItemStack(BTWItems.boiledPotato), new ItemStack(BTWItems.cookedCarrot), new ItemStack(BTWItems.brownMushroom, 3), new ItemStack(BTWItems.flour), new ItemStack(BTWItems.cookedMysteryMeat), new ItemStack(Item.bowlEmpty, 5)});
	}

	@Override
	public void postInitialize() {
		super.postInitialize();
		if (!MinecraftServer.getIsServer()) {
			DebugInfoSection coordinateInfoSection = DebugRegistryUtils.registerSection(loc("coordinates"), DebugRegistryUtils.Side.LEFT);
			coordinateInfoSection.orderSection(DebugRegistry.chunksServerSectionID, 1);
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
					return Optional.of(String.format(Locale.ROOT, "XYZ: %.3f / %.3f / %.3f", mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posZ) + "\n"
							+ String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, string4, yaw, MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch)));
				}
				return Optional.empty();
			});
		}
	}
	public void initializeAchievements() {
		AchievementProvider.NameStep<ItemStack> builder = AchievementProvider.getBuilder(AchievementEvents.ItemEvent.class);
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
		looseCobblestonesTag = Tag.of(loc("loose_cobblestones"),
				new ItemStack(BTWBlocks.looseCobblestone, 1, 0),
				new ItemStack(BTWBlocks.looseCobblestone, 1, 4),
				new ItemStack(BTWBlocks.looseCobblestone, 1, 8));
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

	public void initializeTrades() {
		TradeProvider.getBuilder().name(loc("buy_iron_blocks")).profession(3).level(5).buy().item(Block.blockIron.blockID).itemCount(4, 6).weight(6f).addToTradeList();

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