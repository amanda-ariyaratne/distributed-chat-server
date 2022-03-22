package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.ReserveRoomServerRequest;
import distributed.chat.server.service.server.ReserveRoomServerService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/***
 * Leader side create-room inbound handler to reserve roomId
 *
 * Leader as Server
 * Other Servers as Clients
 */
public class ReserveRoomHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof ReserveRoomServerRequest) {

            // request object
            ReserveRoomServerRequest reserveRoomServerRequest = (ReserveRoomServerRequest) msg;
            System.out.println("Received Reserve Room Server Request " + reserveRoomServerRequest);

            // check validity -> call service
            ReserveRoomServerService reserveRoomServerService = ReserveRoomServerService.getInstance();
            reserveRoomServerService.processRequest(reserveRoomServerRequest,
                    ServerState.serverChannels.get(ServerState.serverConfig.getServer_id()));
        } else {
            ctx.fireChannelRead(msg);
        }

    }
}
