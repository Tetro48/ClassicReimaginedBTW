package net.tetro48.classicaddon.gui;

import btw.community.classicaddon.ClassicAddon;
import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.src.*;
import net.tetro48.classicaddon.ConfigPropertyShell;
import net.tetro48.classicaddon.gui.widget.ConfigOptionListWidget;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ConfigGUI extends GuiScreen {
	private ConfigOptionListWidget list;
	private static final int DONE = 0;

	private GuiScreen previous;
	private String title;
	private boolean permissions;
	private ConfigPropertyShell[] configProperties;

	public ConfigGUI(GuiScreen guiScreen, boolean permissions, ConfigPropertyShell... configProperties) {
		super();
		this.previous = guiScreen;
		this.title = I18n.getString("classicAddon.configTitle");
		this.permissions = permissions;
		this.configProperties = configProperties;
	}

	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 24, I18n.getString("gui.done")));
		this.list = new ConfigOptionListWidget(this.mc, this.width, this.height, 32, this.height - 32, 25, permissions, configProperties);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float delta) {
		this.drawDefaultBackground();
		if (this.list != null) {
			this.list.drawScreen(mouseX, mouseY, delta);
		}
		this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 8, 0xffffff);
		super.drawScreen(mouseX, mouseY, delta);
		if (this.list != null && this.list.tooltip != null) {
			this.renderTooltip(this.list.tooltip, 2, mouseY);
			this.list.tooltip = null;
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
//		if (this.list.isMouseInList(mouseX, mouseY)) {
//			this.list.handleMouse();
//		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		if (this.list.lastTextField != null) {
			this.list.lastTextField.textboxKeyTyped(par1, par2);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		this.list.mouseClicked(mouseX, mouseY, button);
		super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		switch (button.id) {
			case DONE:
				if (this.list.lastTextField != null) {
					this.list.lastConfigProperty.parseSetInternalValue(this.list.lastTextField.getText());
					Minecraft.getMinecraft().getNetHandler().addToSendQueue(new Packet3Chat("/classicreimagined set " + this.list.lastConfigProperty.getPropertyName() + " " + this.list.lastConfigProperty.getInternalValue()));
				}
				this.mc.displayGuiScreen(this.previous);
				break;
		}
	}

	@Override
	public void onGuiClosed() {
		ClassicAddon.addonConfig.readAndWriteConfig();
	}

	protected void renderTooltip(String text, int x, int y) {
		this.renderTooltip(List.of(text), x, y);
	}

	protected void renderTooltip(List<String> text, int x, int y) {
		int n;
		if (text.isEmpty()) {
			return;
		}
		GL11.glDisable(32826);
		Lighting.turnOff();
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		int n2 = 0;
		for (String string : text) {
			n = this.fontRenderer.getStringWidth(string);
			if (n <= n2)
				continue;
			n2 = n;
		}
		int n3 = x + 12;
		int n4 = y - 12;
		n = n2;
		int n5 = 8;
		if (text.size() > 1) {
			n5 += 2 + (text.size() - 1) * 10;
		}
		if (n3 + n2 > this.width) {
			n3 -= 28 + n2;
		}
		if (n4 + n5 + 6 > this.height) {
			n4 = this.height - n5 - 6;
		}
		this.zLevel = 300.0f;
		int n6 = -267386864;
		this.drawGradientRect(n3 - 3, n4 - 4, n3 + n + 3, n4 - 3, n6, n6);
		this.drawGradientRect(n3 - 3, n4 + n5 + 3, n3 + n + 3, n4 + n5 + 4, n6, n6);
		this.drawGradientRect(n3 - 3, n4 - 3, n3 + n + 3, n4 + n5 + 3, n6, n6);
		this.drawGradientRect(n3 - 4, n4 - 3, n3 - 3, n4 + n5 + 3, n6, n6);
		this.drawGradientRect(n3 + n + 3, n4 - 3, n3 + n + 4, n4 + n5 + 3, n6, n6);
		int n7 = 0x505000FF;
		int n8 = (n7 & 0xFEFEFE) >> 1 | n7 & 0xFF000000;
		this.drawGradientRect(n3 - 3, n4 - 3 + 1, n3 - 3 + 1, n4 + n5 + 3 - 1, n7, n8);
		this.drawGradientRect(n3 + n + 2, n4 - 3 + 1, n3 + n + 3, n4 + n5 + 3 - 1, n7, n8);
		this.drawGradientRect(n3 - 3, n4 - 3, n3 + n + 3, n4 - 3 + 1, n7, n7);
		this.drawGradientRect(n3 - 3, n4 + n5 + 2, n3 + n + 3, n4 + n5 + 3, n8, n8);
		for (int i = 0; i < text.size(); ++i) {
			String string = (String) text.get(i);
			this.fontRenderer.drawStringWithShadow(string, n3, n4, -1);
			if (i == 0) {
				n4 += 2;
			}
			n4 += 10;
		}
		this.zLevel = 0.0f;
		GL11.glEnable(2896);
		GL11.glEnable(2929);
		Lighting.turnOn();
		GL11.glEnable(32826);
	}
}
