package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.ReserveIdentityServerRequest;
import distributed.chat.server.model.message.response.server.ReserveIdentityServerResponse;
import distributed.chat.server.service.server.ReserveIdentityServerService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ReserveIdentityHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerRequest = (AbstractMessage) msg;

        if (abstractServerRequest instanceof ReserveIdentityServerRequest){
            ReserveIdentityServerRequest request = (ReserveIdentityServerRequest) abstractServerRequest;

            if (ServerState.serverChannels.size() >= ServerState.servers.size()/2) {
                System.out.println("Process Reserve Identity Server Request " + request);
                ReserveIdentityServerService.getInstance().processRequest(request, ctx.channel());
            } else {
                System.out.println("Reject Reserve Identity Server Request " + request);
                ReserveIdentityServerService.getInstance().sendResponse(
                        new ReserveIdentityServerResponse(request.getIdentity(), true), ctx.channel());
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
