package fr.ushare.fanor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import org.lwjgl.input.Keyboard;

import fr.ushare.fanor.gui.GuiUshare;

/**
 * @author Fanor
 * Client proxy for the mod
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
		keyScreen = new KeyBinding("Screen", Keyboard.KEY_F2, "Ushare");
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
				if(!(mc.currentScreen instanceof GuiControls) || ((GuiControls)mc.currentScreen).time <= Minecraft.getSystemTime() - 20L)
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
