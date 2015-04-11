package fr.usquare.fanor.screen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class FrameHandler{
	
	public static List<Frame> getFrameList(){
		return new ArrayList<Frame>();
	}
	
	//Adds a frame to the main frame list.
	public static void addFrame(Frame frame){
		getFrameList().add(frame);
	}
	
	//Returns the file the frame is stored in.
	public static File getFileFromFrame(Frame frame){
		File returnFile = null;
		
		try{
			for(File file : FrameWriter.getOutputFiles()){
				if(ImageIO.read(file) == frame.getBufferedImage()){
					returnFile = file;
				}
			}
		}catch(IOException exception){
			exception.printStackTrace();
		}
		
		return returnFile;
	}
}
