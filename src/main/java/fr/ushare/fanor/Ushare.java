package fr.ushare.fanor;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import fr.ushare.fanor.cmd.CommandScreen;

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
@Mod(modid = Ushare.MODID, version = Ushare.VERSION)
public class Ushare
{
	/**
	 * Instace of mod
	 */
	@Instance(Ushare.MODID)
	public static Ushare instance;

	/**
	 * Proxy of mod
	 */
	@SidedProxy(clientSide = "fr.ushare.fanor.ClientProxy", serverSide = "fr.ushare.fanor.CommonProxy")
	public static CommonProxy proxy;
	
	/**
	 * Mod ID of mod
	 */
	public static final String MODID = "ushare";
	
	/**
	 * Version of Mod
	 */
	public static final String VERSION = "Beta1.7";
	
	/**
	 * API 
	 */
	private UshareAPI api;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		File directory = new File(Minecraft.getMinecraft().mcDataDir + "//Ushare//Screen");
		
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		Utils.GenererConfig();
		
	}
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRender();	
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		api = new UshareAPI();
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandScreen());
	}
	
	public UshareAPI getApi()
	{
		return this.api;
	}
}
