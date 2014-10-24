package ibm.game.client;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame implements ActionListener {

	/**
	 * Launch the application.
	 */
	

	//static final long serialVersionUID = 2343534;
	private Channel channel = null;
	private ChannelFuture lastWriteFuture = null;
	private EventLoopGroup group = new NioEventLoopGroup();

	private Timer timer = null;
	private String cmd = "";

	public static Key ks = new Key();

	// public Timer keyTimer = new Timer();

	PanelGame pg = new PanelGame();

	Bootstrap b = new Bootstrap();

	static final String HOST = System.getProperty("host",
			"IDEVUSR011.FRANKENI.COM");
	static final int PORT = Integer
			.parseInt(System.getProperty("port", "8998"));

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				GameWindow frame = new GameWindow();

				frame.setVisible(true);

			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameWindow() {
		// getContentPane().setBackground(new Color(51, 153, 255));

		pg.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent arg0) {

				int keycode = arg0.getKeyCode();

				ks.setKeypressed(keycode);

				System.out.println("" + keycode);

				/*
				 * if (keycode != 32 && (keycode < 37 || keycode > 40)) return;
				 * 
				 * cmd = "MOVE:" + gameClientHandler.game.getGameid() + ":" +
				 * keycode + "\r\n";
				 * 
				 * EventQueue.invokeLater(new Runnable() { public void run() {
				 * sendMessage(); } });
				 */

				/*
				 * class KeyPressTask extends TimerTask { private int keycode;
				 * 
				 * public KeyPressTask(int code) { keycode = code; }
				 * 
				 * public void run() { cmd = "MOVE:" +
				 * gameClientHandler.game.getGameid() + ":" + keycode + "\r\n";
				 * 
				 * // EventQueue.invokeLater(new Runnable() { // public void
				 * run() { sendMessage();
				 * 
				 * 
				 * // } // }); timer.schedule(this, 200); }
				 * 
				 * }
				 * 
				 * KeyPressTask tt = new KeyPressTask(keycode);
				 * timer.schedule(tt, 200);
				 */
			}

			public void keyReleased(KeyEvent arg0) {

				int keycode = arg0.getKeyCode();

				System.out.println("" + keycode);

				ks.setKeyreleased(keycode);

				/*
				 * if (keycode != 32 && (keycode < 37 || keycode > 40)) return;
				 * 
				 * cmd = "MOVE:" + gameClientHandler.game.getGameid() + ":" +
				 * keycode + "\r\n";
				 * 
				 * EventQueue.invokeLater(new Runnable() { public void run() {
				 * sendMessage(); } });
				 */

				/*
				 * class KeyPressTask extends TimerTask { private int keycode;
				 * 
				 * public KeyPressTask(int code) { keycode = code; }
				 * 
				 * public void run() { cmd = "MOVE:" +
				 * gameClientHandler.game.getGameid() + ":" + keycode + "\r\n";
				 * 
				 * // EventQueue.invokeLater(new Runnable() { // public void
				 * run() { sendMessage();
				 * 
				 * 
				 * // } // }); timer.schedule(this, 200); }
				 * 
				 * }
				 * 
				 * KeyPressTask tt = new KeyPressTask(keycode);
				 * timer.schedule(tt, 200);
				 */
			}

		});

		this.getContentPane().add(pg);
		pg.requestFocusInWindow();

		b.group(group).channel(NioSocketChannel.class)
				.handler(new gameClientInitializer(this));

		// Start the connection attempt.
		DlgMain dialog = new DlgMain(this, true);
		dialog.setTitle("Tank Game");
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		DlgList dialogList = new DlgList();
		dialogList.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// System.out.println(dialog.getOwner().getClass());

		try {
			channel = b.connect(HOST, PORT).sync().channel();

			dialog.setVisible(true);

			if (dialog.gameWay.equals("NEW"))
				gameClientHandler.game.ID = 1;
			else
				gameClientHandler.game.ID = 2;

			if (gameClientHandler.game.ID == 1)
				lastWriteFuture = channel.writeAndFlush("NEWGAME\r\n");
			else {

				dialogList.putAllGames();
				dialogList.setVisible(true);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				try {
					// channel.closeFuture().sync();

					if (lastWriteFuture != null)
						lastWriteFuture.sync();

				} catch (Exception e) {
					System.out.println(e);
				} finally {
					group.shutdownGracefully();
				}

			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(10, 10, 1200, 800);

		// keyTimer.schedule(new TimerTask(), time);

		timer = new Timer(50, this);

		timer.start();

	}

	public void actionPerformed(ActionEvent evt) {

		final int[] keycodes = { 37, 38, 39, 40 };

		for (int i = 0; i < keycodes.length; i++) {

			if (ks.getKeyPressed(keycodes[i])) {

				cmd = "MOVE:" + gameClientHandler.game.getGameid() + ":"
						+ keycodes[i] + "\r\n";
				sendMessage();

			}

		}

	}

	public void initialGame() {
		setBounds(200, 100, gameClientHandler.game.getWidth(),
				gameClientHandler.game.getHeight());

		getTankImageForMain();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);

		this.setTitle("Tank Game ID: " + gameClientHandler.game.getGameid());

		repaint();

	}

	public void getTankImageForMain() {

		int angle = gameClientHandler.game.getAngle();
		int i = angle % 360 / 10;
		if (i < 0)
			i += 36;
		pg.imgTank = pg.tanks[i];

	}

	public void sendMessage() {

		channel.writeAndFlush(cmd);

	}

}
