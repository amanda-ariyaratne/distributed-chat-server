package distributed.chat.server.handlers.client;

import distributed.chat.server.bootstrap.server.ServerToClient;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractClientRequest;
import distributed.chat.server.model.message.request.NewIdentityClientRequest;
import distributed.chat.server.service.NewIdentityService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NewIdentityHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Client client = new Client(ctx);
        ServerToClient.activeClients.put(ctx.channel().id(), client);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ServerToClient.activeClients.remove(ctx.channel().id());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof NewIdentityClientRequest){
            NewIdentityClientRequest newIdentityClientRequest = (NewIdentityClientRequest) msg;
            newIdentityClientRequest.setSender(ServerToClient.activeClients.get(ctx.channel().id()));
            NewIdentityService newIdentityService = NewIdentityService.getInstance();
            newIdentityService.processRequest(newIdentityClientRequest);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
