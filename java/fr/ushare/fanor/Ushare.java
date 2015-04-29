package fr.ushare.fanor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import scala.collection.parallel.ParIterableLike.Min;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Ushare.MODID, version = Ushare.VERSION)
public class Ushare
{
	@Instance(Ushare.MODID)
	public static Ushare instance;

	@SidedProxy(clientSide = "fr.ushare.fanor.ClientProxy", serverSide = "fr.ushare.fanor.CommonProxy")
	public static CommonProxy proxy;

	
	public static final String MODID = "ushare";
	public static final String VERSION = "Beta1.3";

	
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
}
