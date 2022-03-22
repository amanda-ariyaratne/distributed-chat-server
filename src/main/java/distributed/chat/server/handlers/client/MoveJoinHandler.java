package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.request.client.MoveJoinClientRequest;
import distributed.chat.server.service.client.MoveJoinService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MoveJoinHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // {"type" : "movejoin", "former" : "MainHall-s1", "roomid" : "jokes", "identity" : "Maria"}
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof MoveJoinClientRequest) {
            Client client = ServerState.activeClients.get(ctx.channel().id());

            MoveJoinClientRequest moveJoinClientRequest = (MoveJoinClientRequest) msg;
            moveJoinClientRequest.setSender(client);

            System.out.println("MoveJoinHandler : "+moveJoinClientRequest);

            MoveJoinService.getInstance().processRequest(moveJoinClientRequest);

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
