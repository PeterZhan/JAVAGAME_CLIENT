package ibm.game.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 *  
 * @author c0640045
 *  * 
 *  I searched around and found out it's a limitation in the keyboard
 *  (Logitech Internet Navigator) - it couldn't handle more than 2 consecutive key presses
 *  (sad, I know...). In the end I replaced it with a so-called gaming keyboard 
 *  (Logitech G11), and I haven't had the same problem ever since.
 * 
 * 
 * 
 *
 */

public class PanelGame extends JPanel implements  KeyListener {

	private Image img = null;// ImageIO.read(new File("./resources/land.jpg"));
								// //new
								// ImageIcon("./resources/land.jpg").getImage();

	
	private  boolean isUpPressed, isDownPressed, isSpacePressed, isLeftPressed, isRightPressed;
	
	
	
	
//	public static Key ks = new Key();
	
	public PanelGame() {
		super();
		setOpaque(false); // we don't paint all our bits
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		setFocusable(true);
        addKeyListener(this);

		try {
			img = ImageIO.read(new File("./resources/land.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		

		// setBackground(new Color(51, 153, 255));

		
	}


	@Override
	public Dimension getPreferredSize() {
		// Figure out what the layout manager needs and
		// then add 100 to the largest of the dimensions
		// in order to enforce a 'round' bullseye
		/*
		 * the use of "super." is very important, because otherwise the JRE will
		 * throw a StackOverflowError. And because of the JFrame.pack() used
		 * above, the JFrame window will be resized to adapter the Container
		 * size.
		 */
		Dimension layoutSize = super.getPreferredSize();
		int max = Math.max(layoutSize.width, layoutSize.height);
		return new Dimension(max + 100, max + 100);
	}

	@Override
	protected void paintComponent(Graphics g) {
		/*
		 * Dimension size = getSize(); System.out.println(size.width);
		 * System.out.println(size.height); int x = 0; int y = 0; int i = 0;
		 * while(x < size.width && y < size.height) { g.setColor(i%2==0?
		 * Color.red : Color.white);
		 * g.fillOval(x,y,size.width-(2*x),size.height-(2*y)); x+=10; y+=10;
		 * i++; }
		 */

		g.drawImage(img, 0, 0, gameClientHandler.game.getWidth(),
				gameClientHandler.game.getHeight(), null);

		g.setColor(Color.red);

		g.drawRect(gameClientHandler.game.getC1().getX0() - 20,
				gameClientHandler.game.getC1().getY0() - 20,
				gameClientHandler.game.getC1().getWidth() + 40,
				gameClientHandler.game.getC1().getHeight() + 40);

		g.drawRect(gameClientHandler.game.getC2().getX0() - 20,
				gameClientHandler.game.getC2().getY0() - 20,
				gameClientHandler.game.getC2().getWidth() + 40,
				gameClientHandler.game.getC2().getHeight() + 40);
		// g.setColor(Color.YELLOW);
		int x = gameClientHandler.game.getX() - 20;
		int y = gameClientHandler.game.getY() - 15;

		g.setColor(Color.yellow);
		
		g.drawImage(gameClientHandler.game.getImgTank(), x, y, 42, 42, null);
		
		g.drawString(""+gameClientHandler.game.getFule1(), x-10, y-10);
		
		
		if (gameClientHandler.game.ID == 2 ||(gameClientHandler.game.ID == 1 && gameClientHandler.game.getImgTank2() != null))
		{
			int x2 = gameClientHandler.game.getX2() - 20;
		    int y2 = gameClientHandler.game.getY2() - 15;
		
		    g.drawImage(gameClientHandler.game.getImgTank2(), x2, y2, 42, 42, null);
		    
		    g.setColor(Color.GREEN);
		    
		    g.drawString(""+gameClientHandler.game.getFule2(), x2-10, y2-10);
		
		}
		
		
	    int fireCount = gameClientHandler.game.getCountFires();
	    
	    for (int i=0; i<fireCount; i++)
	    {
	    	fireInfo fi = gameClientHandler.game.getNthFire(i);
	    	
	    	if (fi.time > 0)
	    	{
	    	   if (fi.part == 1)
	    		 g.setColor(Color.yellow);
	    	
	    	   if (fi.part == 2)
	    		 g.setColor(Color.green);
	    	
	    	   g.drawLine(fi.x0, fi.y0, fi.x1, fi.y1);
	    	
	    	 
	    	}
	    	
	    	
	    	
	    }
	    
	   
		
		
		

	}
	
	
	public void keyPressed(KeyEvent arg0) {

		switch(arg0.getKeyCode()) {
		case KeyEvent.VK_UP: setUpPressed(true); break;
        case KeyEvent.VK_DOWN: setDownPressed(true); break;
        case KeyEvent.VK_SPACE: setSpacePressed(true); break;
        case KeyEvent.VK_LEFT: setLeftPressed(true); break;
        case KeyEvent.VK_RIGHT: setRightPressed(true); break;
    }
	
		
		
	/*	int keycode = arg0.getKeyCode();

	//	System.out.println("" + keycode);
		
		ks.setKeypressed(keycode);
    */

		
	}

	public void keyReleased(KeyEvent arg0) {

		switch(arg0.getKeyCode()) {
        case KeyEvent.VK_UP: setUpPressed(false); break;
        case KeyEvent.VK_DOWN: setDownPressed(false); break;
        case KeyEvent.VK_SPACE: setSpacePressed(false); break;
        case KeyEvent.VK_LEFT: setLeftPressed(false); break;
        case KeyEvent.VK_RIGHT: setRightPressed(false); break;
    }	
		
		
	/*	int keycode = arg0.getKeyCode();

	//	System.out.println("" + keycode);

		ks.setKeyreleased(keycode);

		*/
	}
	
	public  synchronized boolean isUpPressed() {
		return isUpPressed;
	}


	public  synchronized void setUpPressed(boolean isUpPressed) {
		this.isUpPressed = isUpPressed;
	}


	public  synchronized boolean isDownPressed() {
		return isDownPressed;
	}


	public synchronized void setDownPressed(boolean isDownPressed) {
		this.isDownPressed = isDownPressed;
	}


	public  synchronized boolean isSpacePressed() {
		return isSpacePressed;
	}


	public  synchronized void setSpacePressed(boolean isSpacePressed) {
		this.isSpacePressed = isSpacePressed;
	}


	public  synchronized boolean isLeftPressed() {
		return isLeftPressed;
	}


	public  synchronized void setLeftPressed(boolean isLeftPressed) {
		this.isLeftPressed = isLeftPressed;
	}


	public  synchronized boolean isRightPressed() {
		return isRightPressed;
	}


	public  synchronized void setRightPressed(boolean isRightPressed) {
		this.isRightPressed = isRightPressed;
	}


	public void keyTyped(KeyEvent ke) {
    }

}
