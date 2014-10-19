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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class GameWindow extends JFrame {

	/**
	 * Launch the application.
	 */
	private Channel channel = null;
	private ChannelFuture lastWriteFuture = null;
	private EventLoopGroup group = new NioEventLoopGroup();
	Bootstrap b = new Bootstrap();

	static final String HOST = System.getProperty("host",
			"IDEVUSR011.FRANKENI.COM");
	static final int PORT = Integer
			.parseInt(System.getProperty("port", "8998"));
	private JTextField textField;

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

		b.group(group).channel(NioSocketChannel.class)
				.handler(new gameClientInitializer(this));

		// Start the connection attempt.

		try {
			channel = b.connect(HOST, PORT).sync().channel();

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
		setBounds(10, 10, 1075, 700);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		JButton btnSendMessage = new JButton("send message");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (channel != null) {
					
					lastWriteFuture = channel.writeAndFlush("NEWGAME\r\n");

				}

			}
		});
		panel.add(btnSendMessage);
		
		textField = new JTextField();
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
	}
	
	
	public void updateText(String t)
	{
		textField.setText(t);
		
		
	}

}
