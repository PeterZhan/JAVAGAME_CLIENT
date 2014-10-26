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
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelGame extends JPanel implements MouseMotionListener {

	private Image img = null;// ImageIO.read(new File("./resources/land.jpg"));
								// //new
								// ImageIcon("./resources/land.jpg").getImage();

	final static int MaxDiv = 36;

	public BufferedImage[] tanks = new BufferedImage[MaxDiv];

	public Image imgTank = null;// this.getToolkit().getImage("./resources/tank0.jpg");
    public Image imgTank2 = null;
	public PanelGame() {
		super();
		setOpaque(false); // we don't paint all our bits
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));

		try {
			img = ImageIO.read(new File("./resources/land.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < MaxDiv; i++) {
			try {
				tanks[i] = ImageIO.read(new File("./resources/tank" + i
						+ ".JPG"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// setBackground(new Color(51, 153, 255));

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

		g.drawImage(imgTank, x, y, 40, 30, null);
		
		
		if (gameClientHandler.game.ID == 2 ||(gameClientHandler.game.ID == 1 && imgTank2 != null))
		{
			int x2 = gameClientHandler.game.getX2() - 20;
		    int y2 = gameClientHandler.game.getY2() - 15;
		
		    g.drawImage(imgTank2, x2, y2, 40, 30, null);
		
		}

	}

}
