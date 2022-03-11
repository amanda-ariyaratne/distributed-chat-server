package distributed.chat.server.handlers.client;

import distributed.chat.server.model.message.AbstractClientRequest;
import distributed.chat.server.model.message.request.NewIdentityClientRequest;
import distributed.chat.server.service.NewIdentityService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NewIdentityHandler extends ChannelInboundHandlerAdapter {

    private NewIdentityService newIdentityService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof NewIdentityClientRequest){
            NewIdentityClientRequest newIdentityClientRequest = (NewIdentityClientRequest) msg;
            newIdentityService = NewIdentityService.getInstance();
            newIdentityService.processRequest(newIdentityClientRequest);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
