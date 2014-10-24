package ibm.game.client;

import java.awt.EventQueue;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class gameClientHandler extends SimpleChannelInboundHandler<String> {

	private GameWindow gw;
	static AGameSession game = new AGameSession();

	public gameClientHandler(GameWindow gw) {
		this.gw = gw;

	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {

		System.out.println(msg);

		String[] msgs = msg.split(":");
		String cmd = msgs[0];

		switch (cmd) {
		case "NEW":
			game.setGameid(msgs[1]);
			String[] size = msgs[2].split(",");
			game.setWidth(Integer.parseInt(size[0]));
			game.setHeight(Integer.parseInt(size[1]));
			String[] po = msgs[4].split(",");
			game.setX(Integer.parseInt(po[0]));
			game.setY(Integer.parseInt(po[1]));
			game.setAngle(Integer.parseInt(msgs[5]));

			String[] xys = msgs[3].split(",");
			game.setC1(new Constraint(xys[0], xys[1], xys[2], xys[3]));
			game.setC2(new Constraint(xys[4], xys[5], xys[6], xys[7]));

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					gw.initialGame();
				}
			});

			break;

		case "POSITION":

			String[] p = msgs[1].split(",");
			game.setX(Integer.parseInt(p[0]));
			game.setY(Integer.parseInt(p[1]));

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					gw.repaint();
				}
			});

			break;

		case "ANGLE":

			int newAngle = Integer.parseInt(msgs[1]);
			game.setAngle(newAngle);

			EventQueue.invokeLater(new Runnable() {
				public void run() {

					gw.getTankImageForMain();
					gw.repaint();
				}
			});

			break;

		case "GL":

			if (msgs.length > 1) {
				AGameSession.namelist = new String[msgs.length - 1];

				for (int i = 1; i < msgs.length; i++)
					AGameSession.namelist[i - 1] = msgs[i];

			}

			break;

		default:
			;

		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
