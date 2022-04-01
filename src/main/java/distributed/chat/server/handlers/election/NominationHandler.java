package distributed.chat.server.handlers.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.election.NominationMessage;
import distributed.chat.server.service.election.NominationService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling nomination message by the election starter
 */
public class NominationHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractMessage = (AbstractMessage) msg;

        if (abstractMessage instanceof NominationMessage){
            NominationMessage message = (NominationMessage) msg;
            // System.out.println("[" + ServerState.localId + " INFO]: " + ServerState.localId + " nominated for the leader by "+ message.getServerId());
            NominationService.getInstance().processMessage(message, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
