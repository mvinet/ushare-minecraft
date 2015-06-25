package fr.ushare.fanor.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import fr.ushare.fanor.Ushare;
import fr.ushare.fanor.Utils;

/**
 * This file (c) by : - Mickael VINET alias fanor79
 *
 * This file is licensed under a
 * GNU GENERAL PUBLIC LICENSE V3.0
 *
 * See the LICENSE file to learn more.
 *
 * If you have contributed to this file, add your name to authors list.
 */
public class GuiUshare extends GuiScreen{

	/**
	 * Save screen
	 */
	private String saveScreen = "On";

	@SuppressWarnings("unchecked")
	public void initGui()
	{

		byte b0 = -16;
		try
		{
			saveScreen = Utils.getSetting("saveScreen");

			if(!Utils.getSetting("loginSuccess").equalsIgnoreCase("true"))
			{
				this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + b0*7, I18n.format("Login", new Object[0])));
			}
			else
			{
				this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + b0*7, I18n.format("Logout", new Object[0])));
			}
			this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + b0*3, 98, 20, I18n.format("Filter", new Object[0])));
			this.buttonList.add(new GuiButton(3, this.width / 2 + 2, this.height / 4 + 120 + b0*3, 98, 20, I18n.format("Save Screen : " + saveScreen, new Object[0])));
			this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120 + b0, I18n.format("menu.returnToGame", new Object[0])));

		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	protected void actionPerformed(GuiButton button)
	{
		try
		{
			switch(button.id)
			{
			case 0:
				this.mc.displayGuiScreen(new GuiUshareLogin());
				break;
			case 1:
				Ushare.instance.getApi().logout();
				this.mc.displayGuiScreen(this);
				break;
			case 2:
				this.mc.displayGuiScreen(new GuiUshareFilter());
				break;
			case 3:
				if(saveScreen.equalsIgnoreCase("on"))
				{
					saveScreen = "Off";
				}
				else
				{
					saveScreen = "On";
				}
				Utils.setSetting("saveScreen", saveScreen);
				button.displayString = "Save Screen : " + saveScreen;
				break;
			case 4:
				this.mc.displayGuiScreen((GuiScreen)null);
				this.mc.setIngameFocus();
				break;
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void drawScreen(int x, int y, float p_73863_3_)
	{
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, I18n.format("Ushare", new Object[0]), this.width / 2, 40, 16777215);
		super.drawScreen(x, y, p_73863_3_);
	}
}
