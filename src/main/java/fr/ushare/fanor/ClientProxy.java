package fr.ushare.fanor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import fr.ushare.fanor.gui.GuiUshare;

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
public class ClientProxy extends CommonProxy{

	/**
	 * Key Binding for Screen
	 */
	public static KeyBinding keyScreen;
	
	/**
	 * Key Binding for Gui
	 */
	public static KeyBinding keyGui;
	
	/**
	 * Minecraft
	 */
	private Minecraft mc;
	
	/**
	 * press
	 */
	private boolean press;

	public ClientProxy()
	{
		FMLCommonHandler.instance().bus().register(this);
		keyScreen = new KeyBinding("Screen", Keyboard.KEY_NONE, "Ushare");
		keyGui = new KeyBinding("Gui", Keyboard.KEY_U, "Ushare");
		press = false;
		mc = Minecraft.getMinecraft();

		ClientRegistry.registerKeyBinding(keyScreen);
		ClientRegistry.registerKeyBinding(keyGui);
	}

	@SubscribeEvent
	public void onClientTickEvent(ClientTickEvent event)
	{
		if(event.phase == Phase.END)
		{
			int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();

			if(i != 0 && !Keyboard.isRepeatEvent())
			{
				if(!(mc.currentScreen instanceof GuiControls))
				{
					if(Keyboard.getEventKeyState())
					{
						if(i == keyScreen.getKeyCode())
						{
							if(!press)
							{
								SendFile sfile = new SendFile("sendfile");
								sfile.start(); 
								this.press = true;
							}
						}
					}
					else
					{
						this.press = false;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEvent(KeyInputEvent event)
	{
		if(keyGui.isPressed())
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiUshare());
		}
	}

}
