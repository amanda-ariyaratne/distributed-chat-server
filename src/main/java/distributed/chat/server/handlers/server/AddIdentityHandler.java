package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.AddIdentityServerRequest;
import distributed.chat.server.service.server.AddIdentityServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AddIdentityHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractServerRequest abstractServerRequest = (AbstractServerRequest) msg;

        if (abstractServerRequest instanceof AddIdentityServerRequest){
            AddIdentityServerRequest request = (AddIdentityServerRequest) abstractServerRequest;
            System.out.println("Received Add Identity Server Request " + request);
            AddIdentityServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
