package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.client.CreateRoomClientRequest;
import distributed.chat.server.model.message.response.client.CreateRoomClientResponse;
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
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof CreateRoomClientRequest) {
            Client client = ServerState.activeClients.get(ctx.channel().id());
            String roomId = ((CreateRoomClientRequest) msg).getRoomId();
            CreateRoomService createRoomService = CreateRoomService.getInstance();

            if (ServerState.serverChannels.size() >= ServerState.servers.size()/2) {
                CreateRoomClientRequest createRoomClientRequest = (CreateRoomClientRequest) msg;
                createRoomClientRequest.setSender(client);
                System.out.println("\nINFO: Processing Create Room Client Request " + createRoomClientRequest);

                ServerState.reservedRooms.put(roomId, client);
                System.out.println("Reserved " + roomId);

                createRoomService.processRequest(createRoomClientRequest);

            } else {
                createRoomService.sendResponse(new CreateRoomClientResponse(roomId, false), client);
            }

        } else {
            ctx.fireChannelRead(msg);
        }

    }
}
