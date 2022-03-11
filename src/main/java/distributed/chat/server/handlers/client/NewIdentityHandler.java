package distributed.chat.server.handlers.client;

import distributed.chat.server.model.message.request.NewIdentityClientRequest;
import distributed.chat.server.service.NewIdentityService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class NewIdentityHandler extends ChannelInboundHandlerAdapter {

    private NewIdentityService newIdentityService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        /**
         *  Todo: Add client id to the client list
         */
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        /**
         * Todo: Remove client id from client list
         */
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof NewIdentityClientRequest){
            NewIdentityClientRequest request = (NewIdentityClientRequest) msg;
            newIdentityService = NewIdentityService.getInstance();
            newIdentityService.handleRequest(request);
        } else
            ctx.fireChannelRead(msg);
    }
}
