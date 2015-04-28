package fr.ushare.fanor.gui;

import fr.ushare.fanor.Ushare;
import fr.ushare.fanor.Utils;
import fr.ushare.fanor.screen.Filter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiUshareFilter extends GuiScreen{

	public void initGui()
	{
		byte b0 = -16;
		String color = "none";
		try {
			color = Utils.getSetting("color");
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + b0 * 3, I18n.format(color, new Object[0])));

		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120 + b0, I18n.format("Back", new Object[0])));
	}

	protected void actionPerformed(GuiButton button)
	{
		try {
			switch(button.id)
			{
			case 2:
				if(button.displayString.equals("none"))
				{
					button.displayString = "red";

					Utils.setSetting("color", "red");

				}
				break;

			case 4:
				this.mc.displayGuiScreen(new GuiUshare());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawScreen(int x, int y, float p_73863_3_)
	{
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, I18n.format("Ushare Filter", new Object[0]), this.width / 2, 40, 16777215);
		super.drawScreen(x, y, p_73863_3_);
	}
}
