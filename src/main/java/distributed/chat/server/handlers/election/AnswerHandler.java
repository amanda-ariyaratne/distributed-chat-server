package distributed.chat.server.handlers.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.election.AnswerMessage;
import distributed.chat.server.service.election.AnswerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for answering to an election request
 */
public class AnswerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractMessage = (AbstractMessage) msg;

        if (abstractMessage instanceof AnswerMessage){
            AnswerMessage message = (AnswerMessage) msg;

            // System.out.println("INFO: " + "answer response from "+ message.getServerId());

            AnswerService.getInstance().processMessage(message, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
