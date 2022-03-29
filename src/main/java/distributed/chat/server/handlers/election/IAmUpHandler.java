package distributed.chat.server.handlers.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.election.IAmUpMessage;
import distributed.chat.server.service.election.IAmUpService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling I am up messages
 */
public class IAmUpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractMessage = (AbstractMessage) msg;

        if (abstractMessage instanceof IAmUpMessage){
            IAmUpMessage message = (IAmUpMessage) msg;
            System.out.println("INFO: " + "iamup message sent by "+ message.getServerId());
            IAmUpService.getInstance().processMessage(message, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }

}
