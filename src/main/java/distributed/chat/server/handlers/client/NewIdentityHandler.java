package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.request.client.NewIdentityClientRequest;
import distributed.chat.server.service.client.NewIdentityService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NewIdentityHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Client client = new Client(ctx);
        ServerState.activeClients.put(ctx.channel().id(), client);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ServerState.activeClients.remove(ctx.channel().id());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof NewIdentityClientRequest){
            Client client = ServerState.activeClients.get(ctx.channel().id());

            NewIdentityClientRequest newIdentityClientRequest = (NewIdentityClientRequest) msg;
            newIdentityClientRequest.setSender(client);
            System.out.println("Received New Identity Client Request " + msg);

            ServerState.reservedClients.put(newIdentityClientRequest.getIdentity(), client);
            System.out.println("Reserved new identity " + newIdentityClientRequest.getIdentity());
            NewIdentityService newIdentityService = NewIdentityService.getInstance();
            newIdentityService.processRequest(newIdentityClientRequest);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
