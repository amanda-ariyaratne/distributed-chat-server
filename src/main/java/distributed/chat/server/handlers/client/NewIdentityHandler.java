package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.client.NewIdentityClientRequest;
import distributed.chat.server.model.message.response.client.NewIdentityClientResponse;
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
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof NewIdentityClientRequest){
            NewIdentityClientRequest newIdentityClientRequest = (NewIdentityClientRequest) msg;
            Client client = ServerState.activeClients.get(ctx.channel().id());

            newIdentityClientRequest.setSender(client);

            NewIdentityService newIdentityService = NewIdentityService.getInstance();

            if (ServerState.serverChannels.size() >= ServerState.servers.size()/2) {
                System.out.println("Processing New Identity Client Request " + msg);
                newIdentityService.processRequest(newIdentityClientRequest);
            } else {
                System.out.println("Rejecting New Identity Client Request " + msg);
                newIdentityService.sendResponse(new NewIdentityClientResponse(false), client);
            }

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
