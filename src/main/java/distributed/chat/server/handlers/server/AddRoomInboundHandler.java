package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.AddRoomServerRequest;
import distributed.chat.server.service.server.AddRoomServerService;
import distributed.chat.server.service.server.ReserveRoomServerService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AddRoomInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // {"type" : "addroom", "serverid" : "s1", "roomid" : "jokes"}
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof AddRoomServerRequest) {
            // request object
            AddRoomServerRequest addRoomServerRequest = (AddRoomServerRequest) msg;
            System.out.println("Received Add Room Server Request " + addRoomServerRequest);
            // call service -> AddRoomServerService
            AddRoomServerService addRoomServerService = AddRoomServerService.getInstance();
            addRoomServerService.handleRequest(addRoomServerRequest,
                    ServerState.serverChannels.get(ServerState.serverConfig.getServer_id()));
        }
    }
}
