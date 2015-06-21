package fr.ushare.fanor;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
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
	@Instance(Ushare.MODID)
	public static Ushare instance;

	@SidedProxy(clientSide = "fr.ushare.fanor.ClientProxy", serverSide = "fr.ushare.fanor.CommonProxy")
	public static CommonProxy proxy;
	
	public static final String MODID = "ushare";
	public static final String VERSION = "Beta1.4";
	
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
	{}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandScreen());
	}
}
