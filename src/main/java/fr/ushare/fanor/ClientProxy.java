package fr.ushare.fanor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import org.lwjgl.input.Keyboard;

import fr.ushare.fanor.gui.GuiUshare;

public class ClientProxy extends CommonProxy{

	public static KeyBinding keyScreen;
	public static KeyBinding keyGui;
	private GuiIngame guiIngame;
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
		int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
		Minecraft mc = Minecraft.getMinecraft();

		if (i != 0 && !Keyboard.isRepeatEvent())
		{
			if (!(mc.currentScreen instanceof GuiControls) || ((GuiControls)mc.currentScreen).time <= mc.getSystemTime() - 20L)
			{
				if (Keyboard.getEventKeyState())
				{
					if(i == keyScreen.getKeyCode())
					{
					      
						SendFile sfile = new SendFile("sendfile");
						sfile.start();
					}
				}
			}
		}
		if(keyGui.isPressed())
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiUshare());
		}
	}



}
