package fr.usquare.fanor.screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

import net.minecraft.client.Minecraft;

public class Frame extends BufferedImage{
	private int frameID;
	private int[] pixelArray;

	public Frame(BufferedImage image, int frameID){
		super(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		this.frameID = frameID;
		if(FrameWriter.getOutput() == null){
			FrameWriter.setOutput(Minecraft.getMinecraft().mcDataDir + "//Usquare");
		}
		loadFrame(image);
	}
	
	//Sets the frame's size.
	public Frame setSize(int width, int height){
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(getBufferedImage(), 0, 0, width, height, null);
		g.dispose();
		return new Frame(resizedImage, getFrameID());
	}

	public WritableRaster getRaster(){
		return getData().createCompatibleWritableRaster();
	}
	
	//Sets the frame's pixel data.
	public void setFrame(int[] intArray){
		this.setRGB(0, 0, getWidth(), getHeight(), intArray, 0, getWidth());
	}
	
	//Loads the frame from the image.
	public void loadFrame(BufferedImage image){
		setFrame(((DataBufferInt) image.getRaster().getDataBuffer()).getData());
	}
	
	//Returns the pixel data of the frame.
	public int[] getPixelArray(){
		return pixelArray;
	}

	public void setFrameID(int id){
		frameID = id;
	}

	public int getFrameID(){
		return frameID;
	}

	//Returns the image during initialization, used with filters.
	public BufferedImage getBufferedImage(){
		return this.getSubimage(0, 0, getWidth(), getHeight());
	}
	
	//Returns a new frame from the image.
	public static Frame getFrameFromBufferedImage(BufferedImage image){
		return new Frame(image, FrameHandler.getFrameList().size() + 1);
	}
}
