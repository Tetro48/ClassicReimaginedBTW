package net.tetro48.classicaddon.gui.widget;

import btw.community.classicaddon.ClassicAddon;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.tetro48.classicaddon.ModifiableConfigProperty;
import net.tetro48.classicaddon.gui.widget.entries.EntryListWidget;
import net.tetro48.classicaddon.mixin.GuiTextFieldAccessor;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ConfigOptionListWidget extends EntryListWidget {
	private final Minecraft minecraft;
	private final List<Entry> entries = new ArrayList<>();

	private int nextId;
	public List<String> tooltip;
	public GuiTextField lastTextField;
	public ModifiableConfigProperty lastConfigProperty;

	public ConfigOptionListWidget(Minecraft minecraft, int width, int height, int yStart, int yEnd, int entryHeight, boolean permissions, ModifiableConfigProperty ... options) {
		super(minecraft, width, height, yStart, yEnd, entryHeight);
		this.minecraft = minecraft;
//        this.centerAlongY = false;
		for (int i = 0; i < options.length; i++) {
			ModifiableConfigProperty option = options[i];
			this.entries.add(new Entry(width, option, permissions));
		}
	}

	@Nullable
	private static GuiButton createWidget(final Minecraft minecraft, int id, int x, int y, int width, final @Nullable ModifiableConfigProperty option) {
		if (option == null) {
			return null;
		}
		GuiButton button = new GuiSmallButton(id, x, y, null, option.getExternalValue().toString());
		button.width = width;
		return button;
	}

	@Override
	public Entry getEntry(int i) {
		return this.entries.get(i);
	}

	@Override
	protected int getSize() {
		return this.entries.size();
	}

	@Override
	public int getRowWidth() {
		return 400;
	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 32;
	}

	public boolean isMouseInList(int mouseX, int mouseY) {
		return mouseY >= this.top && mouseY <= this.bottom && mouseX >= this.right && mouseX <= this.left;
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {

	}

	public final class Entry implements EntryListWidget.Entry {
		@Nullable
		private final ModifiableConfigProperty configProperty;
		@Nullable
		private final GuiButton booleanButton;
		private final GuiTextField textField;

		private boolean previousTextFieldFocus;


		public Entry(int x, @Nullable ModifiableConfigProperty option, boolean permissions) {
			if (option.getInternalValue() instanceof Boolean) {
				booleanButton = ConfigOptionListWidget.createWidget(minecraft, nextId++, x / 2, 0, 100, option);
				booleanButton.enabled = permissions;
				textField = null;
				configProperty = option;
			}
			else {
				booleanButton = null;
				textField = new GuiTextField(mc.fontRenderer,x / 2, 0, 100, 20);
				textField.setText(option.getInternalValue().toString());
				textField.setEnabled(permissions);
				configProperty = option;
			}
		}

		@Override
		public void render(int index, int x, int y, int width, int height, BufferBuilder bufferBuilder, int mouseX, int mouseY, boolean hovered) {
			mc.fontRenderer.drawStringWithShadow(I18n.getString(configProperty.getPropertyName()), 15, y, 0xFFFFFF);
			GL11.glTranslatef(15, y + 10, 0);
			GL11.glScalef(0.5f, 0.5f, 0.5f);
			List<String> descriptionStringList = mc.fontRenderer.listFormattedStringToWidth(I18n.getString(configProperty.getPropertyName()+".desc"), width);
			for (int i = 0; i < descriptionStringList.size(); i++) {
				mc.fontRenderer.drawStringWithShadow(descriptionStringList.get(i), 0, i * 10, 0x7F7F7F);
			}
			GL11.glScalef(2f, 2f, 2f);
			GL11.glTranslatef(-15, -y - 10, 0);
			if (hovered) {
				tooltip = mc.fontRenderer.listFormattedStringToWidth(I18n.getString(configProperty.propertyName + ".desc"), width);
			}
			if (this.booleanButton != null) {
				this.booleanButton.yPosition = y;
				this.booleanButton.displayString = configProperty.getInternalValue().toString();
				this.booleanButton.drawButton(minecraft, mouseX, mouseY);
			}
			if (this.textField != null) {
				((GuiTextFieldAccessor)this.textField).setYPos(y);
				this.textField.drawTextBox();
			}
		}

		@Override
		public boolean mouseClicked(int index, int mouseX, int mouseY, int button, int entryMouseX, int entryMouseY) {
			if (button == 0) {
				if (this.booleanButton != null && this.booleanButton.mousePressed(minecraft, mouseX, mouseY)) {
					if (configProperty.getInternalValue() instanceof Boolean) {
						configProperty.setInternalValue(!(Boolean)configProperty.getInternalValue());
						Minecraft.getMinecraft().getNetHandler().addToSendQueue(new Packet3Chat("/classicreimagined set " + configProperty.getPropertyName() + " " + configProperty.getInternalValue()));
					}
					minecraft.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
					return true;
				}
				if (this.textField != null) {
					this.textField.mouseClicked(mouseX, mouseY, button);
					if (textField.isFocused() && lastTextField != textField) {
						if (lastTextField != null) {
							System.out.println(lastTextField.getText());
							lastConfigProperty.parseSetInternalValue(lastTextField.getText());
							Minecraft.getMinecraft().getNetHandler().addToSendQueue(new Packet3Chat("/classicreimagined set " + lastConfigProperty.getPropertyName() + " " + lastConfigProperty.getInternalValue()));
						}
						lastTextField = textField;
						lastConfigProperty = configProperty;
					}
					minecraft.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
					return true;
				}
			}
			return false;
		}

		@Override
		public void mouseReleased(int index, int mouseX, int mouseY, int button, int entryMouseX, int entryMouseY) {
			if (booleanButton != null) this.booleanButton.mouseReleased(mouseX, mouseY);
		}
	}
}
