package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.AddIdentityServerRequest;
import distributed.chat.server.service.server.AddIdentityServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling new identity list syncing
 */
public class AddIdentityHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerRequest = (AbstractMessage) msg;

        if (abstractServerRequest instanceof AddIdentityServerRequest){
            AddIdentityServerRequest request = (AddIdentityServerRequest) abstractServerRequest;

            System.out.println("INFO: " + "add identity " + request.getIdentity() + " to the global clients list");

            AddIdentityServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
