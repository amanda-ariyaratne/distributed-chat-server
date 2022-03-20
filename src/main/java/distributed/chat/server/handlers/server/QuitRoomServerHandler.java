package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.QuitServerRequest;
import distributed.chat.server.service.server.QuitServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class QuitRoomServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractServerRequest abstractServerRequest = (AbstractServerRequest) msg;

        if (abstractServerRequest instanceof QuitServerRequest){
            QuitServerRequest request = (QuitServerRequest) abstractServerRequest;
            QuitServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
