package distributed.chat.server.handlers.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.election.ViewMessage;
import distributed.chat.server.service.election.ViewService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling view messages sent by the servers
 */
public class ViewHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractMessage = (AbstractMessage) msg;
        if (abstractMessage instanceof ViewMessage){
            ViewMessage message = (ViewMessage) msg;
            System.out.println("INFO: " + "received view message from "+ message.getServerId());
            ViewService.getInstance().processMessage(message, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }

}
