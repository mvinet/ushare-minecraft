package fr.usquare.fanor.screen;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CameraProperties implements IExtendedEntityProperties{

	public static final String SKILL_ID = "CameraAPI";
	private final EntityPlayer player;
	
	public CameraProperties(EntityPlayer player){
		this.player = player;
	}
	
	public static final void registerProperties(EntityPlayer player){
		player.registerExtendedProperties(SKILL_ID, new CameraProperties(player));
	}
	
	public static final CameraProperties getSkillProperties(EntityPlayer player){
		return (CameraProperties) player.getExtendedProperties(SKILL_ID);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound){
		NBTTagCompound properties = new NBTTagCompound();
		compound.setTag(SKILL_ID, properties);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound){
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(SKILL_ID);
    	
		try{
			for(File file : FrameWriter.getOutputFiles()){
	    		FrameHandler.getFrameList().add(Frame.getFrameFromBufferedImage(ImageIO.read(file)));
	    	}
		}catch(IOException exception){
			exception.printStackTrace();
		}
	}

	@Override
	public void init(Entity entity, World world){
		
	}
}
