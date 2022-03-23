package distributed.chat.server.handlers.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.election.CoordinatorMessage;
import distributed.chat.server.service.election.CoordinatorService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for electing as coordinator message from the coordinator
 */
public class CoordinatorHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractMessage = (AbstractMessage) msg;

        if (abstractMessage instanceof CoordinatorMessage){
            CoordinatorMessage message = (CoordinatorMessage) msg;
            System.out.println("INFO: " + "coordinator response from "+ message.getServerId());
            CoordinatorService.getInstance().processMessage(message, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
