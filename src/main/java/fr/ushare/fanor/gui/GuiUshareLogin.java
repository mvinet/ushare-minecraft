package fr.ushare.fanor.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

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
public class GuiUshareLogin extends GuiScreen {

	/**
	 * Input Login
	 */
	private GuiTextField textLogin;

	/**
	 * Input Password
	 */
	private GuiTextPassword textPassword;

	@SuppressWarnings("unchecked")
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);

		byte b0 = -16;

		this.textLogin = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, this.height / 4 + 120 + b0*6, 95, 20);
		this.textLogin.setText("login");

		this.textPassword = new GuiTextPassword(this.fontRendererObj, this.width / 2 + 2, this.height / 4 + 120 + b0*6, 95, 20);
		this.textPassword.setText("password");

		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + b0 * 4, I18n.format("Login", new Object[0])));

		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120 + b0, I18n.format("menu.returnToGame", new Object[0])));
	}

	public void updateScreen()
	{
		this.textLogin.updateCursorCounter();
		this.textPassword.updateCursorCounter();
	}

	protected void keyTyped(char typedChar, int keyCode)
	{
		if(this.textLogin.isFocused())
		{
			this.textLogin.textboxKeyTyped(typedChar, keyCode);
		}
		else if(this.textPassword.isFocused())
		{
			this.textPassword.textboxKeyTyped(typedChar, keyCode);
		}
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		this.textLogin.mouseClicked(mouseX, mouseY, mouseButton);
		this.textPassword.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
		case 2:
			Ushare.instance.getApi().login(this.textLogin, this.textPassword);
			try
			{
				if(Utils.getSetting("loginSuccess").equalsIgnoreCase("true"))
				{
					this.mc.displayGuiScreen((GuiScreen)null);
					this.mc.setIngameFocus();
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			break;
		case 4:
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
			break;
		}
	}

	public void drawScreen(int x, int y, float p_73863_3_)
	{
		this.textLogin.drawTextBox();
		this.textPassword.drawTextBox();

		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, I18n.format("Login", new Object[0]), this.width / 2, 40, 16777215);
		super.drawScreen(x, y, p_73863_3_);
	}


}

