package fr.ushare.fanor;

import fr.ushare.fanor.screen.FrameWriter;

/**
 * This file (c) by : - Mickael VINET alias fanor79
 *
 * This file is licensed under a
 * GNU GENERAL PUBLIC LICENSE V3.0
 *
 * See the LICENSE file to learn more.
 *
 * If you have contributed to this file, add your name to authors list.
*/
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
