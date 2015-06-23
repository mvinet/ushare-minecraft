package fr.ushare.fanor;

import fr.ushare.fanor.screen.FrameWriter;

public class SendFile extends Thread{

	
	public SendFile(String name)
	{
		super(name);
	}

	@SuppressWarnings("deprecation")
	public void run()
	{
		FrameWriter.screen();
		this.stop();
	}

	
}
