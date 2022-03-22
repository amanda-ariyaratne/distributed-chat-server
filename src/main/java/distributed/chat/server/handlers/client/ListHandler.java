package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.request.client.ListClientRequest;
import distributed.chat.server.service.client.ListService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ListHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof ListClientRequest){
            Client client = ServerState.activeClients.get(ctx.channel().id());

            ListClientRequest listClientRequest = (ListClientRequest) msg;
            listClientRequest.setSender(client);

            System.out.println("ListHandler : "+listClientRequest);

            // get service
            ListService listService = ListService.getInstance();
            listService.processRequest(listClientRequest);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
