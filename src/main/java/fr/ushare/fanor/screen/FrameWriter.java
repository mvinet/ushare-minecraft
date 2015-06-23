package fr.ushare.fanor.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
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
import fr.ushare.fanor.Ushare;
import fr.ushare.fanor.Utils;

@SuppressWarnings("deprecation")
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

	public static void screen()
	{

		BufferedImage image = CameraHelper.takeScreenShot();
		Frame frame = Frame.getFrameFromBufferedImage(image);
		FrameWriter.saveFrameAsImage(frame);

	}

	//Saves the frame to the output file.
	public static void saveFrameAsImage(Frame frame){
		try{
			//	Filter.overlayFrameFromColor(frame, Color.RED, 0.4f);
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			File output = new File(outputFile, "screenshot_" + dateFormat.format(new Date()).toString() + ".jpg");

			if(!Utils.getSetting("color").equalsIgnoreCase("none"))
			{
				Filter.overlayFrameFromColor(frame, Utils.getColor(Utils.getSetting("color")), Float.parseFloat(Utils.getSetting("transparency"))/100F);
			}

			ImageIO.write(frame.getBufferedImage(), "jpg", output);

			sendPost(output);

		}catch(IOException exception){
			exception.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "resource" })
	public static void sendPost(File file)
	{
		String picUrl = "";
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost httppost = new HttpPost("http://api.ushare.so/file/upload");

			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file, "image/jpeg");
			mpEntity.addPart("file", cbFile);
			mpEntity.addPart("source", new StringBody("official_minecraft_ushare_" + Ushare.VERSION));

			httppost.setEntity(mpEntity);
			System.out.println("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				picUrl = EntityUtils.toString(resEntity);
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Link copied in clipboard !"));
				Utils.Copier(picUrl);
			}
			if (resEntity != null) {
				resEntity.consumeContent();
			}

			httpclient.getConnectionManager().shutdown();
			ChatComponentText chat = new ChatComponentText("Screen Saved");
			chat.getChatStyle().setChatClickEvent(new ClickEvent(Action.OPEN_URL, picUrl));
			chat.getChatStyle().setUnderlined(true);
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(chat);
			
			file.delete();

		}
		catch(Exception e)
		{
			try{
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Error"));
			}catch(Exception ex)
			{
				
			}
			e.printStackTrace();
		}



	}

}