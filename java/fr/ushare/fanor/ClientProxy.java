package fr.ushare.fanor;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import org.lwjgl.input.Keyboard;

import fr.ushare.fanor.gui.GuiUshare;
import fr.ushare.fanor.screen.CameraHelper;
import fr.ushare.fanor.screen.Frame;
import fr.ushare.fanor.screen.FrameWriter;

public class ClientProxy extends CommonProxy{

	private static KeyBinding keyScreen;
	private static KeyBinding keyGui;
	
	public ClientProxy()
	{
		FMLCommonHandler.instance().bus().register(this);
		keyScreen = new KeyBinding("Screen", Keyboard.KEY_U, "Ushare");
		keyGui = new KeyBinding("Gui", Keyboard.KEY_Y, "Ushare");
		
		ClientRegistry.registerKeyBinding(keyScreen);
		ClientRegistry.registerKeyBinding(keyGui);
	}


	@SubscribeEvent
	public void onEvent(KeyInputEvent event)
	{
		if(keyScreen.isPressed())
		{
			
			SendFile sfile = new SendFile("sendfile");
			sfile.start();
		}
		
		if(keyGui.isPressed())
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiUshare());
		}
	}

	

}
