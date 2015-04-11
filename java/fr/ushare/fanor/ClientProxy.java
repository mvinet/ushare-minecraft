package fr.ushare.fanor;

import java.awt.image.BufferedImage;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import fr.ushare.fanor.screen.CameraHelper;
import fr.ushare.fanor.screen.Frame;
import fr.ushare.fanor.screen.FrameWriter;

public class ClientProxy extends CommonProxy{

	private static KeyBinding keyBind;

	public ClientProxy()
	{

		FMLCommonHandler.instance().bus().register(this);
		keyBind = new KeyBinding("Screen", Keyboard.KEY_U, "Ushare");
		ClientRegistry.registerKeyBinding(keyBind);
	}


	@SubscribeEvent
	public void onEvent(KeyInputEvent event)
	{
		if(keyBind.isPressed())
		{
			screen();
		}
	}

	public void screen()
	{
		BufferedImage image = CameraHelper.takeScreenShot();
		Frame frame = Frame.getFrameFromBufferedImage(image);
		FrameWriter.saveFrameAsImage(frame);

	}

}
