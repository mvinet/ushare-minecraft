package fr.ushare.fanor.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import fr.ushare.fanor.Utils;

public class GuiUshareFilter extends GuiScreen{

	public String color = "none";
	public int transparency = 0;

	public void initGui()
	{
		byte b0 = -16;

		try {
			color = Utils.getSetting("color");

			transparency = Integer.parseInt(Utils.getSetting("transparency"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + b0 * 3, 98, 20, I18n.format(color, new Object[0])));
		this.buttonList.add(new GuiButton(3, this.width / 2 + 2, this.height / 4 + 120 + b0 * 3, 98, 20, I18n.format("" + transparency, new Object[0])));

		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120 + b0, I18n.format("Back", new Object[0])));
	}

	protected void actionPerformed(GuiButton button)
	{
		try {
			switch(button.id)
			{
			case 2:
				switch(button.displayString)
				{
				case "none":
					button.displayString = "red";
					Utils.setSetting("color", "red");
					break;
				case "red":
					button.displayString = "green";
					Utils.setSetting("color", "green");
					break;
				case "green":
					button.displayString = "blue";
					Utils.setSetting("color", "blue");
					break;
				case "blue":
					button.displayString = "none";
					Utils.setSetting("color", "none");
				}
				break;

			case 3:
				if(transparency < 100)
				{
					transparency = transparency + 10;
				}
				else
				{
					transparency = 0;
				}
				button.displayString = "" + transparency;
				Utils.setSetting("transparency", transparency + "");

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
