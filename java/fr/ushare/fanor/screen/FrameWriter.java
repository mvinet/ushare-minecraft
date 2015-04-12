package fr.ushare.fanor.screen;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import fr.ushare.fanor.Ushare;

public class FrameWriter{

	private static File outputFile;
	public int writerID;

	public FrameWriter(int id){
		writerID = id;
	}

	//Sets the output file of the images.
	public static void setOutput(File file){
		outputFile = file;
	}

	//Sets the output file of the images.
	public static void setOutput(String location){
		outputFile = new File(location);
	}

	//Returns the output file, used for saving images.
	public static File getOutput(){
		return outputFile;
	}

	//Returns the files inside of the output file, used for loading in images.
	public static List<File> getOutputFiles(){
		return Arrays.asList(outputFile.listFiles());
	}

	//Saves the frame to the output file.
	public static void saveFrameAsImage(Frame frame){
		try{
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			File output = new File(outputFile, "screenshot_" + dateFormat.format(new Date()).toString() + ".jpg");
			ImageIO.write(frame.getBufferedImage(), "jpg", output);
			
			sendPost(output);
		}catch(IOException exception){
			exception.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private static void sendPost(File file)
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
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(picUrl));
				System.out.println(picUrl);
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
