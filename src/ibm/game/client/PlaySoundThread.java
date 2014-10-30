package ibm.game.client;

import java.io.*;

import sun.audio.*;

public class PlaySoundThread extends Thread {
	
	AudioStream audioStream;
	InputStream in;
	
	public PlaySoundThread (String f) throws Exception
	{
		
         in = new FileInputStream(f);
     	   
     
	
		audioStream = new AudioStream(in);
		
		
	}


	public void run()
	{
		
			  
	    AudioPlayer.player.start(audioStream);
		
	   
		
		
	}
	
	

}
