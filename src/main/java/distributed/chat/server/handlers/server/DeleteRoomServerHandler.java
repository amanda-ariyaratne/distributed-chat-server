package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.DeleteRoomServerRequest;
import distributed.chat.server.service.server.DeleteRoomServerService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling delete room syncing
 */
public class DeleteRoomServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerRequest = (AbstractMessage) msg;

        if (abstractServerRequest instanceof DeleteRoomServerRequest){
            DeleteRoomServerRequest request = (DeleteRoomServerRequest) abstractServerRequest;

            System.out.println("[" + ServerState.localId + " INFO]: " + "delete room " + request.getRoomId() + " from the global room list");

            DeleteRoomServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
