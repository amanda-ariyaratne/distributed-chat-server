package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.request.server.ReserveIdentityConfirmServerRequest;
import distributed.chat.server.model.message.response.server.AbstractServerResponse;
import distributed.chat.server.model.message.response.server.ReserveIdentityServerResponse;
import distributed.chat.server.service.server.ReserveIdentityConfirmServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ReserveIdentityConfirmHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractServerResponse abstractServerResponse = (AbstractServerResponse) msg;

        if (abstractServerResponse instanceof ReserveIdentityServerResponse){
            ReserveIdentityServerResponse response = (ReserveIdentityServerResponse) abstractServerResponse;
            System.out.println("Received Reserve Identity Server response " + response);

            ReserveIdentityConfirmServerService.getInstance().processRequest(new ReserveIdentityConfirmServerRequest(
                    response.isReserved(),
                    response.getIdentity()
            ), ctx.channel());

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
