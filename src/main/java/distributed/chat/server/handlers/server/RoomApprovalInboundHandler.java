package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.ReserveRoomServerRequest;
import distributed.chat.server.service.server.ReserveRoomServerService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/***
 * Leader side create-room inbound handler
 *
 * Leader as Server
 * Other Servers as Clients
 */
public class RoomApprovalInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}
        AbstractServerRequest request = (AbstractServerRequest) msg;
        if (request instanceof ReserveRoomServerRequest) {

            // request object
            ReserveRoomServerRequest reserveRoomServerRequest = (ReserveRoomServerRequest) msg;

            // check validity -> call service
            ReserveRoomServerService reserveRoomServerService = ReserveRoomServerService.getInstance();
            reserveRoomServerService.handleRequest(reserveRoomServerRequest,
                    ServerState.serverChannels.get(ServerState.serverConfig.getServer_id()));
        }

    }
}
