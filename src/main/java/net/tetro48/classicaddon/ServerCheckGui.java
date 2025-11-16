package net.tetro48.classicaddon;

import btw.community.classicaddon.ClassicAddon;
import net.minecraft.src.*;

public class ServerCheckGui extends GuiScreen {
	private final GuiScreen previousGui;
	private int ticksUntilKick;

	public ServerCheckGui(GuiScreen guiScreen, Minecraft minecraft) {
		this.mc = minecraft;
		this.previousGui = guiScreen;
		ticksUntilKick = 200;
	}

	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if (guiButton.id == 0) {
			this.mc.theWorld.sendQuittingDisconnectingPacket();
			this.mc.loadWorld(null);
			this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
		}

	}

	//stop players from doing "ESC" trick
	@Override
	protected void keyTyped(char par1, int par2) {}

	@Override
	public void updateScreen() {
		if (!ClassicAddon.isServerRunningThisAddon) {
			this.ticksUntilKick--;
			if (this.ticksUntilKick <= 0) {
				Packet255KickDisconnect kickDisconnect = new Packet255KickDisconnect("Kicked by Classic Re-Imagined addon");
				this.mc.getNetHandler().quitWithPacket(kickDisconnect);
				this.mc.loadWorld(null);
				this.mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.timeout", "disconnect.failedCheckForCR"));
			}
		}
		else {
			this.mc.displayGuiScreen(previousGui);
		}
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.getString("menu.disconnect")));
	}


	@Override
	public void drawScreen(int i, int j, float f) {
		if (!ClassicAddon.isServerRunningThisAddon) {
			this.drawDefaultBackground();
			this.drawCenteredString(this.fontRenderer, I18n.getString("classicAddon.serverCheck.doesServerHaveCR1"), this.width / 2, this.height / 2 - 50, 16777215);
			this.drawCenteredString(this.fontRenderer, I18n.getString("classicAddon.serverCheck.doesServerHaveCR2"), this.width / 2, this.height / 2 - 40, 16777215);
			this.drawCenteredString(this.fontRenderer, I18n.getStringParams("classicAddon.serverCheck.kickTimer", MathHelper.ceiling_double_int(ticksUntilKick / 20d)), this.width / 2, this.height / 2 - 10, 16777215);
			super.drawScreen(i, j, f);
		}
	}
}
