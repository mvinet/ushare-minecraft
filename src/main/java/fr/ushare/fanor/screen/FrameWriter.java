package fr.ushare.fanor.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import fr.ushare.fanor.Ushare;
import fr.ushare.fanor.Utils;

public class FrameWriter{

	/**
	 * outputFile
	 */
	private static File outputFile;
	
	/**
	 * writeID
	 */
	public int writerID;

	/**
	 * UshareAPI;
	 */
	
	public FrameWriter(int id){
		writerID = id;
	}

	//Sets the output file of the images.
	public static void setOutput(File file){
		outputFile = file;
	}

	//Sets the output file of the images.
	public static void setOutput(String location){
		outputFile = new File(location);
	}

	//Returns the output file, used for saving images.
	public static File getOutput(){
		return outputFile;
	}

	//Returns the files inside of the output file, used for loading in images.
	public static List<File> getOutputFiles(){
		return Arrays.asList(outputFile.listFiles());
	}

	public static void screen()
	{

		BufferedImage image = CameraHelper.takeScreenShot();
		Frame frame = Frame.getFrameFromBufferedImage(image);
		FrameWriter.saveFrameAsImage(frame);

	}

	//Saves the frame to the output file.
	public static void saveFrameAsImage(Frame frame){
		try{
			//	Filter.overlayFrameFromColor(frame, Color.RED, 0.4f);
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			File output = new File(outputFile, "screenshot_" + dateFormat.format(new Date()).toString() + ".jpg");

			if(!Utils.getSetting("color").equalsIgnoreCase("none"))
			{
				Filter.overlayFrameFromColor(frame, Utils.getColor(Utils.getSetting("color")), Float.parseFloat(Utils.getSetting("transparency"))/100F);
			}

			ImageIO.write(frame.getBufferedImage(), "jpg", output);

			Ushare.instance.getApi().sendFile(output);

		}catch(IOException exception){
			exception.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}