package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.AddRoomServerRequest;
import distributed.chat.server.service.server.AddRoomServerService;
import distributed.chat.server.service.server.ReserveRoomServerService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Inbound Handler for handling new room syncing
 *
 * {"type" : "addroom", "serverid" : "s1", "roomid" : "jokes"}
 *
 */
public class AddRoomInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // {"type" : "addroom", "serverid" : "s1", "roomid" : "jokes"}
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof AddRoomServerRequest) {
            // request object
            AddRoomServerRequest addRoomServerRequest = (AddRoomServerRequest) msg;
            // call service -> AddRoomServerService
            AddRoomServerService addRoomServerService = AddRoomServerService.getInstance();

            System.out.println("INFO: " + "add room " + addRoomServerRequest.getRoomId() + " to the global room list");

            addRoomServerService.handleRequest(addRoomServerRequest,
                    ServerState.serverChannels.get(ServerState.serverConfig.getServer_id()));
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
