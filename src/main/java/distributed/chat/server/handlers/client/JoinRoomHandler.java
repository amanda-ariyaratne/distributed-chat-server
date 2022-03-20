package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.request.client.JoinRoomClientRequest;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class JoinRoomHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof JoinRoomClientRequest){
            Client client = ServerState.activeClients.get(ctx.channel().id());

            JoinRoomClientRequest joinRoomClientRequest = (JoinRoomClientRequest) msg;

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
