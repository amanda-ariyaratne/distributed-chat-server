package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.request.client.CreateRoomClientRequest;
import distributed.chat.server.model.message.request.client.NewIdentityClientRequest;
import distributed.chat.server.service.client.CreateRoomIdentityService;
import distributed.chat.server.service.client.NewIdentityService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CreateRoomHandler extends ChannelInboundHandlerAdapter {

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
//        // get activeClient
//        // check if the client is already has a room
//        //
//        Client client = new Client(ctx);
//        Room room = new Room()
//        ServerState.activeRooms.put(ctx.channel().id(), client);
//    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        // client inactive -> delete room -> remove other clients and add them to mainhall
    }

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
            // set sender
            createRoomClientRequest.setSender(client);
            // add to reserved rooms
            String roomId = ((CreateRoomClientRequest) msg).getRoomId();
            ServerState.reservedRooms.put(roomId, client);
            // validation and create room -> call service class
            CreateRoomIdentityService createRoomIdentityService = CreateRoomIdentityService.getInstance();
            createRoomIdentityService.processRequest(createRoomClientRequest);
        } else {
            ctx.fireChannelRead(msg);
        }

    }
}
