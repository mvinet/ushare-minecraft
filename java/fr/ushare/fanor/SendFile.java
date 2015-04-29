package fr.ushare.fanor;

import java.awt.image.BufferedImage;
import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import fr.ushare.apache.MultipartEntity;
import fr.ushare.apache.content.ContentBody;
import fr.ushare.apache.content.FileBody;
import fr.ushare.apache.content.StringBody;
import fr.ushare.fanor.screen.CameraHelper;
import fr.ushare.fanor.screen.Frame;
import fr.ushare.fanor.screen.FrameWriter;

public class SendFile extends Thread{

	
	public SendFile(String name)
	{
		super(name);
	}

	public void run()
	{
		FrameWriter.screen();
		this.stop();
	}

	
}
