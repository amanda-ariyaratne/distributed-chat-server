package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.client.WhoClientRequest;
import distributed.chat.server.service.client.WhoService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class WhoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //{"type" : "who"}
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof WhoClientRequest){
            Client client = ServerState.activeClients.get(ctx.channel().id());

            WhoClientRequest whoClientRequest = (WhoClientRequest) msg;
            whoClientRequest.setSender(client);

            System.out.println("\nWho Client request : "+ whoClientRequest);

            WhoService whoService = WhoService.getInstance();
            whoService.processRequest(whoClientRequest);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
