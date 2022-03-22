package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.request.client.DeleteRoomClientRequest;
import distributed.chat.server.service.client.DeleteRoomService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DeleteRoomHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // {"type" : "deleteroom", "roomid" : "jokes"}
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof DeleteRoomClientRequest) {
            Client client = ServerState.activeClients.get(ctx.channel().id());
            System.out.println("delete room handler : msg " + msg);
            DeleteRoomClientRequest deleteRoomClientRequest = (DeleteRoomClientRequest) msg;
            deleteRoomClientRequest.setSender(client);

            DeleteRoomService.getInstance().processRequest(deleteRoomClientRequest);

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
