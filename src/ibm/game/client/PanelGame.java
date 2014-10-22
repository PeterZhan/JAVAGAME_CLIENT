package ibm.game.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;

public class PanelGame extends JPanel implements MouseMotionListener {

	
	private Image img = new ImageIcon("").getImage(./resources/land.jpg");
	public PanelGame() {
		super();
		setOpaque(false); // we don't paint all our bits
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
	//	setBackground(new Color(51, 153, 255));
		
		
		
		addMouseMotionListener(this);
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		boolean status = this.requestFocusInWindow();
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
		g.drawImage(img, 0, 0, gameClientHandler.game.getX(),gameClientHandler.game.getX(), null);
		
		g.setColor(Color.YELLOW);
		int x = gameClientHandler.game.getX() - 10;
		int y = gameClientHandler.game.getY() - 10;

		g.fillOval(x, y, 20, 20);

	}

}
