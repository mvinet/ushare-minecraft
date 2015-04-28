package fr.ushare.fanor.screen;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import fr.ushare.fanor.Ushare;

public class Filter{

	
	//Overlays a filter with the set color and transparency.
	public static void overlayFrameFromColor(Frame frame, Color color, float transparency){
		BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		BufferedImage image2 = frame.getBufferedImage();
		g.drawImage(image2, 0, 0, null);
		g.setColor(color);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, transparency));
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		frame.loadFrame(image);
	}

	//Sets the frame to a gray/greyscale version.
	public static void setToGrayscale(Frame frame){
		BufferedImage bufferedImage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = bufferedImage.getGraphics();
		g.drawImage(frame.getBufferedImage(), 0, 0, null);
		g.dispose();
		frame.setFrame(bufferedImage.getRGB(0, 0, frame.getWidth(), frame.getHeight(), null, 0, frame.getWidth()));
	}
	
	//Below 1.0F and above 0.0F will darken, Above 1.0F will brighten, very sensitive.
	public static void setBrightness(Frame frame, float brightness){
		BufferedImage bufferedImage = frame.getBufferedImage();
		new RescaleOp(brightness, 0, null).filter(bufferedImage, bufferedImage);
		frame.setFrame(bufferedImage.getRGB(0, 0, frame.getWidth(), frame.getHeight(), null, 0, frame.getWidth()));
	}
}
