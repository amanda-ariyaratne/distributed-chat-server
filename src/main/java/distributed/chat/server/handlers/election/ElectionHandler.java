package distributed.chat.server.handlers.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.election.ElectionMessage;
import distributed.chat.server.service.election.ElectionService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for starting an election
 */
public class ElectionHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractMessage = (AbstractMessage) msg;

        if (abstractMessage instanceof ElectionMessage){
            ElectionMessage message = (ElectionMessage) msg;
            // System.out.println(ServerState.localId + " INFO: " + "election started by "+ message.getServerId());
            ElectionService.getInstance().processMessage(message, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
