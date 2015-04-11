package fr.usquare.fanor;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler {

	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent e)
	{
		if(e.entity instanceof EntityPlayer)
		{
			try 
			{
				URL url = new URL("http://dev.fanor79.com/usquare/version.txt");
				InputStream ins = url.openStream();
				BufferedReader txt = new BufferedReader(new InputStreamReader(ins));
				String urlv = txt.readLine();
				if(!Usquare.VERSION.equalsIgnoreCase(urlv))
				{
					Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("La version " + urlv + " de UsquareMod est maintenant disponible sur http://google.fr"));	
				}

			} 
			catch (Exception e2) 
			{
			}
		}
	}
}
