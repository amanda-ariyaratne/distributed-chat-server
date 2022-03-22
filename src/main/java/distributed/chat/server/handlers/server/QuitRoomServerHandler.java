package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.QuitServerRequest;
import distributed.chat.server.service.server.QuitServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class QuitRoomServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerRequest = (AbstractMessage) msg;

        if (abstractServerRequest instanceof QuitServerRequest){
            QuitServerRequest request = (QuitServerRequest) abstractServerRequest;
            System.out.println("QuitRoomServerHandler Request "+request);
            QuitServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
