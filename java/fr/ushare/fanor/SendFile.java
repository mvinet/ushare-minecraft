package fr.ushare.fanor;

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

public class SendFile extends Thread{

	private File file;
	
	public SendFile(String name, File file)
	{
		super(name);
		this.file = file;
	}

	public void run()
	{
		sendPost(file);
		this.stop();
	}

	@SuppressWarnings("resource")
	public static void sendPost(File file)
	{
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost httppost = new HttpPost("http://usqua.re/file/upload/");

			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file, "image/jpeg");
			mpEntity.addPart("file", cbFile);
			mpEntity.addPart("version", new StringBody("official_minecraft_ushare_" + Ushare.VERSION));

			httppost.setEntity(mpEntity);
			System.out.println("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				String picUrl = EntityUtils.toString(resEntity);
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Copied in clipboard !"));
				Utils.Copier(picUrl);
			}
			if (resEntity != null) {
				resEntity.consumeContent();
			}

			httpclient.getConnectionManager().shutdown();

			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Screen Save"));
		}
		catch(Exception e)
		{
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Error"));
			e.printStackTrace();
		}
		
	
	}
}
