package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.client.MessageClientRequest;
import distributed.chat.server.service.client.MessageService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling messages sent to a room
 */
public class MessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof MessageClientRequest){
            Client client = ServerState.activeClients.get(ctx.channel().id());

            MessageClientRequest messageClientRequest = (MessageClientRequest) msg;
            messageClientRequest.setSender(client);

            System.out.println("INFO: " + "message request from "+ client.getIdentity() + " to the room " + client.getRoom().getRoomId());

            MessageService.getInstance().processRequest(messageClientRequest);

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
