package ibm.game.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
import javax.swing.JTextField;

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

public class GameWindow extends JFrame {

	/**
	 * Launch the application.
	 */
	private Channel channel = null;
	private ChannelFuture lastWriteFuture = null;
	private EventLoopGroup group = new NioEventLoopGroup();

	private Timer timer = new Timer();
	private String cmd = "";
	
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
		getContentPane().setBackground(new Color(51, 153, 255));

		
		pg.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent arg0) {

				int keycode = arg0.getKeyCode();

				System.out.println("" + keycode);

				if (keycode < 37 || keycode > 40)
					return;

				cmd = "MOVE:" + gameClientHandler.game.getGameid() + ":"
						+ keycode + "\r\n";

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						sendMessage();
					}
				});

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

		try {
			channel = b.connect(HOST, PORT).sync().channel();
			lastWriteFuture = channel.writeAndFlush("NEWGAME\r\n");

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
		setBounds(10, 10, 1200, 800);
	}

	public void initialGame() {
		setBounds(200, 100, gameClientHandler.game.getWidth(),
				gameClientHandler.game.getHeight());

		getTankImageForMain();
		
		repaint();

	}
	
	
	
	public void getTankImageForMain() {

		int angle = gameClientHandler.game.getAngle();
		int i = angle % 360 / 10;
		if (i<0) i += 36;
		pg.imgTank = pg.getToolkit().getImage("./resources/tank" + i + ".jpg");	
		

	}
	
	
	

	public void sendMessage() {

		channel.writeAndFlush(cmd);

	}

}
