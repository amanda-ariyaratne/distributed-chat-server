package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.client.DeleteRoomClientRequest;
import distributed.chat.server.service.client.DeleteRoomService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling delete room
 */
public class DeleteRoomHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof DeleteRoomClientRequest) {
            Client client = ServerState.activeClients.get(ctx.channel().id());
            DeleteRoomClientRequest deleteRoomClientRequest = (DeleteRoomClientRequest) msg;
            deleteRoomClientRequest.setSender(client);

            System.out.println("INFO: " + "delete room request from "+ client.getIdentity() + " for the room " + deleteRoomClientRequest.getRoomId());

            DeleteRoomService.getInstance().processRequest(deleteRoomClientRequest);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
