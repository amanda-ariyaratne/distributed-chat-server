package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.client.NewIdentityClientRequest;
import distributed.chat.server.model.message.response.client.NewIdentityClientResponse;
import distributed.chat.server.service.client.NewIdentityService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling new client join request
 */
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
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof NewIdentityClientRequest){
            NewIdentityClientRequest newIdentityClientRequest = (NewIdentityClientRequest) msg;
            Client client = ServerState.activeClients.get(ctx.channel().id());

            newIdentityClientRequest.setSender(client);

            NewIdentityService newIdentityService = NewIdentityService.getInstance();

            System.out.println("INFO: " + "new identity request from "+ client.getIdentity());

            if (ServerState.serverChannels.size() >= ServerState.servers.size()/2) {
                System.out.println("INFO: Processing New Identity Client Request");
                newIdentityService.processRequest(newIdentityClientRequest);
            } else {
                System.out.println("WARN: Minimum required number of servers missing");
                System.out.println("WARN: Rejecting New Identity Client Request");
                newIdentityService.sendResponse(new NewIdentityClientResponse(false), client);
            }

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
