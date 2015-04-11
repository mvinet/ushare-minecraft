package fr.ushare.fanor;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Usquare.MODID, version = Usquare.VERSION)
public class Usquare
{
	@Instance(Usquare.MODID)
	public static Usquare instance;
	
	@SidedProxy(clientSide = "fr.ushare.fanor.ClientProxy", serverSide = "fr.ushare.fanor.CommonProxy")
	public static CommonProxy proxy;
	
	public static final String MODID = "ushare";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    	File directory = new File(Minecraft.getMinecraft().mcDataDir + "//Ushare");
		if(!directory.exists()){
			directory.mkdirs();
		}
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRender();	
    	MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    	
    	
    }
}
