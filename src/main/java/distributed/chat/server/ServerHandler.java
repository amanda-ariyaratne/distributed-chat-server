package distributed.chat.server;

import distributed.chat.server.model.message.AbstractClientRequest;
import distributed.chat.server.service.AbstractClientService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        AbstractClientRequest request = (AbstractClientRequest) msg;
        try {
            serveRequest(request);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void serveRequest(AbstractClientRequest request) {
        // TODO
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
