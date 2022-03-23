package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.AddRoomServerRequest;
import distributed.chat.server.model.message.request.server.SyncGlobalListsServerRequest;
import distributed.chat.server.service.server.SyncGlobalListsServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SyncGlobalListsHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerRequest = (AbstractMessage) msg;
        if (abstractServerRequest instanceof AddRoomServerRequest) {
            SyncGlobalListsServerRequest request = (SyncGlobalListsServerRequest) abstractServerRequest;
            SyncGlobalListsServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
