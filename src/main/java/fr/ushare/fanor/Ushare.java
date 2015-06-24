package fr.ushare.fanor;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import fr.ushare.fanor.cmd.CommandScreen;

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
	public static final String VERSION = "Beta1.5";
	
	/**
	 * API 
	 */
	public UshareAPI api;
	
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
