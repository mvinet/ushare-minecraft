package fr.ushare.fanor;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import fr.ushare.apache.MultipartEntity;
import fr.ushare.apache.content.ContentBody;
import fr.ushare.apache.content.FileBody;
import fr.ushare.apache.content.StringBody;

/**
 * This file (c) by : - Mickael VINET alias fanor79
 *
 * This file is licensed under a
 * GNU GENERAL PUBLIC LICENSE V3.0
 *
 * See the LICENSE file to learn more.
 *
 * If you have contributed to this file, add your name to authors list.
*/
@SuppressWarnings("deprecation")
public class UshareAPI {

	/**
	 * login Success
	 */
	private boolean loginSuccess = false;

	/**
	 * API allow send file
	 * @param file the file to upload
	 */
	@SuppressWarnings("resource")
	public void sendFile(File file)
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
			mpEntity.addPart("source", new StringBody("minecraft_" + Ushare.VERSION));

			httppost.setEntity(mpEntity);
			System.out.println("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				picUrl = EntityUtils.toString(resEntity);
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Link copied in clipboard !"));
				Utils.Copier(picUrl);

				resEntity.consumeContent();
			}

			httpclient.getConnectionManager().shutdown();

			ChatComponentText chat = new ChatComponentText("Screen Uploaded");
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

	/**
	 * API allow login with credentials
	 * @param login GuiTextField of login
	 * @param password GuiTextField of password
	 */
	@SuppressWarnings("resource")
	public void login(GuiTextField login, GuiTextField password)
	{
		login.setEnabled(false);
		password.setEnabled(false);
		String result = "";

		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost httppost = new HttpPost("http://api.ushare.so/user/auth");

			MultipartEntity mpEntity = new MultipartEntity();

			mpEntity.addPart("username", new StringBody(login.getText()));
			mpEntity.addPart("password", new StringBody(Utils.toSHA256(password.getText())));
			mpEntity.addPart("source", new StringBody("minecraft_" + Ushare.VERSION));

			httppost.setEntity(mpEntity);
			System.out.println("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			result = EntityUtils.toString(resEntity);

			System.out.println(response.getStatusLine());
			System.out.println("json : " + result);

			resEntity.consumeContent();
			httpclient.getConnectionManager().shutdown();

			this.loginSuccess = Utils.toJSON(result).get("success").getAsBoolean();

			if(loginSuccess)
			{
				Utils.setSetting("accountkey", Utils.toJSON(result).get("accountkey").getAsString());
				Utils.setSetting("privatekey", Utils.toJSON(result).get("privatekey").getAsString());
			}
			else
			{
				JOptionPane.showMessageDialog(new JFrame(), "Login or Password Incorect", "Credentials Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		login.setEnabled(true);
		password.setEnabled(true);
	}

	/**
	 * API get Info
	 */
	@SuppressWarnings("resource")
	public void info()
	{
		
		StringBuilder body = new StringBuilder();
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpGet httpget = new HttpGet("http://api.ushare.so/user/info");
		
		try
		{
			httpget.addHeader("accountkey", Utils.getSetting("accountkey"));
			httpget.addHeader("privatekey", Utils.getSetting("privatekey"));
			
			httpget.addHeader("source", "minecraft_" + Ushare.VERSION);

			HttpResponse response = httpclient.execute(httpget);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(statusCode == HttpStatus.SC_OK)
			{
				HttpEntity e = response.getEntity();
				String entity = EntityUtils.toString(e);
				body.append(entity);
			}
			else
			{
				body.append(statusLine + "\n");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			httpget.releaseConnection();
		}
		
		System.out.println(body.toString());
	}

	/**
	 * @return loginSuccess
	 */
	public boolean getLoginSuccess()
	{
		return this.loginSuccess;
	}
}
