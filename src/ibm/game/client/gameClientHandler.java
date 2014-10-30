package ibm.game.client;

import java.awt.EventQueue;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class gameClientHandler extends SimpleChannelInboundHandler<String> implements Runnable{

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
		
		
		 case "NEWJOIN":
			//game.setGameid(msgs[1]);
			String[] po = msgs[4].split(",");
			game.setX2(Integer.parseInt(po[0]));
			game.setY2(Integer.parseInt(po[1]));
			game.setAngle2(Integer.parseInt(msgs[5]));
			
			
			if (game.ID == 2)
			{
			   String[] size = msgs[2].split(",");
			   game.setWidth(Integer.parseInt(size[0]));
			   game.setHeight(Integer.parseInt(size[1]));
			//   game.setAngle(Integer.parseInt(msgs[5]));
			
			  String[] xys = msgs[3].split(",");
			  game.setC1(new Constraint(xys[0], xys[1], xys[2], xys[3]));
			  game.setC2(new Constraint(xys[4], xys[5], xys[6], xys[7]));

			  EventQueue.invokeLater(new Runnable() {
				    public void run() {
					     gw.initialGameForSec();
				    }
			   });
			 
			}
			if (game.ID == 1){
				
				game.getTankImageForSec();
				
				EventQueue.invokeLater(this);
				//gw.repaint();
				
				
			}
			
			
			
            
			
			break;
		
		
		
		case "NEW":
			game.setGameid(msgs[1]);
			String[] size = msgs[2].split(",");
			game.setWidth(Integer.parseInt(size[0]));
			game.setHeight(Integer.parseInt(size[1]));
			po = msgs[4].split(",");
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
			
			int part = Integer.parseInt(msgs[2]);
			
			
			if (part == 1)
			{
			   game.setX(Integer.parseInt(p[0]));
			   game.setY(Integer.parseInt(p[1]));

			}
			
			if (part == 2)
			{
				game.setX2(Integer.parseInt(p[0]));
				game.setY2(Integer.parseInt(p[1]));
				
			}
			   
			 EventQueue.invokeLater(this);/* 
			   EventQueue.invokeLater(new Runnable() {
				public void run() {
					gw.repaint();
				}
			  });*/
			
			
			
			
			
			
			break;

		case "ANGLE":

			int newAngle = Integer.parseInt(msgs[1]);
			part  = Integer.parseInt(msgs[2]);
			
			if (part == 1)
			{
			  game.setAngle(newAngle);
			  game.getTankImageForMain();
			  EventQueue.invokeLater(this);
			 /* EventQueue.invokeLater(new Runnable() {
					public void run() {

						
						gw.repaint();
					}
				});*/
			  
			}
			else
			{
			  game.setAngle2(newAngle);
			  game.getTankImageForSec();
			  EventQueue.invokeLater(this);/*new Runnable() {
					public void run() {

						
						gw.repaint();
					}
				});*/
			  
			}

		

			break;

		case "GL":

			if (msgs.length > 1) {
				
				String names[] = msgs[1].split(",");
 				AGameSession.namelist = names;//new String[msgs.length - 1];

			/*	for (int i = 1; i < msgs.length; i++)
					AGameSession.namelist[i - 1] = msgs[i];  */

			}
			AGameSession.setGotGames(true);

			break;
			
		case "FIRE":
           fireInfo fi = new fireInfo();
           part = Integer.parseInt(msgs[3]);
           fi.part = part;
		   String[] ps = msgs[1].split(",");
		   fi.x0 = Integer.parseInt(ps[0]);
		   fi.y0 = Integer.parseInt(ps[1]);
		   fi.x1 = Integer.parseInt(ps[2]);
		   fi.y1 = Integer.parseInt(ps[3]);
		   
		   fi.targeted = Boolean.parseBoolean(msgs[2]);
		   
		   game.addNewFireInfo(fi);
			
			
		   EventQueue.invokeLater(this);
			

			break;		
			
			
			
		case "SCORE":
			
			part  = Integer.parseInt(msgs[2]);
			int score = Integer.parseInt(msgs[1]);
			
			if (part == 1)
			{
               game.setFule1(score);
               
               
			}   
			
			if (part == 2)
			{
				game.setFule2(score);
				
			}
			
			EventQueue.invokeLater(this);
			
			
			if (score <= 0)
			{
				
				if (part == 1)
				{
					game.setImgTank(game.imgExp);
					
					
				}
				
				if (part == 2)
				{
					game.setImgTank2(game.imgExp);
					
					
				}
				
				
				if (game.ID == part)
					game.setEndMsg("You lose!");
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {						
						gw.showEndMsg();
					}
				
				
			});
			
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

	
	public void run() {

		
		gw.repaint();
	}
}
