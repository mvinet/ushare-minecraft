package fr.ushare.fanor.gui;

import fr.ushare.fanor.Ushare;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiUshare extends GuiScreen{

	// GuiIngameMenu
	public void initGui()
	{
		byte b0 = -16;

		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + b0*3, I18n.format("Filter", new Object[0])));

		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120 + b0, I18n.format("menu.returnToGame", new Object[0])));

	}

	protected void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
		case 1:
			this.mc.displayGuiScreen(new GuiUshareFilter());
			break;
		
		 case 4:
             this.mc.displayGuiScreen((GuiScreen)null);
             this.mc.setIngameFocus();
             break;
		}
	}

	public void drawScreen(int x, int y, float p_73863_3_)
	{
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, I18n.format("Ushare", new Object[0]), this.width / 2, 40, 16777215);
		super.drawScreen(x, y, p_73863_3_);
	}
}
