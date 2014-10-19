package ibm.game.client;

import java.awt.EventQueue;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class gameClientHandler extends SimpleChannelInboundHandler<String> {

       private GameWindow gw;
       private String text="";
	
	   public gameClientHandler(GameWindow gw)
	   {
		  this.gw = gw;
		
		
	    }
	
	    @Override
	    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
	    	text += (msg + "\r\n");
	    	EventQueue.invokeLater(new Runnable() {
				public void run() {
					
					//gw.getComponentAt(p)
                   // gw.repaint();
					gw.updateText(text);
					gw.repaint();
				}
			});
	        
	    }

	
	    
	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();
	        ctx.close();
	    }

}
