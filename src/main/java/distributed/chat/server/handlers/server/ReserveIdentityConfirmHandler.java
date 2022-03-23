package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.ReserveIdentityConfirmServerRequest;
import distributed.chat.server.model.message.response.server.ReserveIdentityServerResponse;
import distributed.chat.server.service.server.ReserveIdentityConfirmServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling reserve confirm responses
 */
public class ReserveIdentityConfirmHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerResponse = (AbstractMessage) msg;

        if (abstractServerResponse instanceof ReserveIdentityServerResponse){
            ReserveIdentityServerResponse response = (ReserveIdentityServerResponse) abstractServerResponse;

            System.out.println("INFO: " + "recieved reserve confirm responses for " + response.getIdentity());

            ReserveIdentityConfirmServerService.getInstance().processRequest(new ReserveIdentityConfirmServerRequest(
                    response.isReserved(),
                    response.getIdentity()
            ), ctx.channel());

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
