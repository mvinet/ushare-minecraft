package fr.ushare.fanor.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import fr.ushare.fanor.Ushare;

public class GuiUshare extends GuiScreen{

	@SuppressWarnings("unchecked")
	public void initGui()
	{
		byte b0 = -16;

		if(!Ushare.instance.api.getLoginSuccess())
		{
			this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + b0*7, I18n.format("Login", new Object[0])));
		}
		
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + b0*3, I18n.format("Filter", new Object[0])));

		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120 + b0, I18n.format("menu.returnToGame", new Object[0])));

	}
    
	protected void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
		case 0:
			Ushare.instance.api.info();
			//this.mc.displayGuiScreen(new GuiUshareLogin());
			break;
			
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
