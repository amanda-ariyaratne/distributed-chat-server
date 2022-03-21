package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.request.client.CreateRoomClientRequest;
import distributed.chat.server.service.client.CreateRoomService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for reading requests from the clients
 */
public class CreateRoomHandler extends ChannelInboundHandlerAdapter {

    /***
     * {"type" : "createroom", "roomid" : "jokes"}
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof CreateRoomClientRequest) {
            // get active client
            Client client = ServerState.activeClients.get(ctx.channel().id());
            // create message with new room id
            CreateRoomClientRequest createRoomClientRequest = (CreateRoomClientRequest) msg;
            System.out.println("Received Create Room Client Request " + createRoomClientRequest);
            // set sender
            createRoomClientRequest.setSender(client);
            // add to reserved rooms
            String roomId = ((CreateRoomClientRequest) msg).getRoomId();
            ServerState.reservedRooms.put(roomId, client);
            System.out.println("Reserved " + roomId);
            // validation and create room -> call service class
            CreateRoomService createRoomService = CreateRoomService.getInstance();
            createRoomService.processRequest(createRoomClientRequest);
        } else {
            ctx.fireChannelRead(msg);
        }

    }
}
