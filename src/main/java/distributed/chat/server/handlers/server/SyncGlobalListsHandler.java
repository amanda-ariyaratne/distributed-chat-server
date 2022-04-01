package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.AddRoomServerRequest;
import distributed.chat.server.model.message.request.server.SyncGlobalListsServerRequest;
import distributed.chat.server.service.server.SyncGlobalListsServerService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling syncing global lists
 */
public class SyncGlobalListsHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerRequest = (AbstractMessage) msg;
        if (abstractServerRequest instanceof AddRoomServerRequest) {
            SyncGlobalListsServerRequest request = (SyncGlobalListsServerRequest) abstractServerRequest;

            System.out.println("[" + ServerState.localId + " INFO]: Received sync lists request");

            SyncGlobalListsServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
