package fr.ushare.fanor.screen;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.Display;


@SideOnly(Side.CLIENT)
public class CameraHelper{
	
    //Creates a screenshot object of your Minecraft window.
    public static BufferedImage takeScreenShot(){
    	return recordScreenshot();
    }

    private static BufferedImage recordScreenshot(){
        try{
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Display.getX() + 8, Display.getY() + 30, Display.getWidth(), Display.getHeight()));
            return image;
        }catch(Exception exception){
            exception.printStackTrace();
            return null;
        }
    }
}
