package ibm.game.client;
import java.util.*;
import io.netty.channel.Channel;

public class AGameSession {
    	
	private String gameid;
	
	private int width;
	private int height;
	
	int x;
	int y;
	
	int angle = 0;
	
	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public synchronized int  getWidth() {
		return width;
	}

	public synchronized void setWidth(int width) {
		this.width = width;
	}

	public synchronized int getHeight() {
		return height;
	}

	public synchronized void setHeight(int height) {
		this.height = height;
	}

	
		
	

	public synchronized String  getGameid()  {
		return gameid;
	}

	public AGameSession()
	{
	
			
	}


	public synchronized void setGameid(String gameid) {
		this.gameid = gameid;
	}
	
	
	
	
	
	

}
