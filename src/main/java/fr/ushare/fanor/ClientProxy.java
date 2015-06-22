package fr.ushare.fanor;

import net.minecraft.client.Minecraft;
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

public class ClientProxy extends CommonProxy{

	public static KeyBinding keyScreen;
	public static KeyBinding keyGui;
	private Minecraft mc;

	public ClientProxy()
	{
		FMLCommonHandler.instance().bus().register(this);
		keyScreen = new KeyBinding("Screen", Keyboard.KEY_U, "Ushare");
		keyGui = new KeyBinding("Gui", Keyboard.KEY_Y, "Ushare");

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
							if(Keyboard.next()){
								System.out.println("ok");
								
							}
							//SendFile sfile = new SendFile("sendfile");
							//sfile.start(); 
						}
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
