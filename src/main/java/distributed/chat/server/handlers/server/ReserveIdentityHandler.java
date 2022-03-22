package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.ReserveIdentityServerRequest;
import distributed.chat.server.service.server.ReserveIdentityServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ReserveIdentityHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerRequest = (AbstractMessage) msg;

        if (abstractServerRequest instanceof ReserveIdentityServerRequest){
            ReserveIdentityServerRequest request = (ReserveIdentityServerRequest) abstractServerRequest;
            System.out.println("Received Reserve Identity Server Request " + request);
            ReserveIdentityServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
