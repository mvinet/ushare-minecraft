package fr.usquare.fanor;

import java.awt.MouseInfo;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import fr.usquare.fanor.screen.CameraHelper;
import fr.usquare.fanor.screen.Frame;
import fr.usquare.fanor.screen.FrameWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ScreenShotHelper;

public class ClientProxy extends CommonProxy{

	private static KeyBinding keyBind;

	public ClientProxy()
	{

		FMLCommonHandler.instance().bus().register(this);
		keyBind = new KeyBinding("Screen", Keyboard.KEY_U, "Uplimg");
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
