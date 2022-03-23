package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.ReserveRoomServerRequest;
import distributed.chat.server.model.message.response.server.ReserveRoomServerResponse;
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

    /***
     *
     * @param ctx = context of the channel
     * @param msg = msg from channel
     * @throws Exception
     *
     * {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof ReserveRoomServerRequest) {

            ReserveRoomServerService reserveRoomServerService = ReserveRoomServerService.getInstance();
            ReserveRoomServerRequest reserveRoomServerRequest = (ReserveRoomServerRequest) msg;

            if (ServerState.serverChannels.size() >= ServerState.servers.size()/2) {
                System.out.println("Process Reserve Room Server Request " + reserveRoomServerRequest);
                reserveRoomServerService.processRequest(reserveRoomServerRequest,
                        ServerState.serverChannels.get(ServerState.serverConfig.getServer_id()));

            } else {
                System.out.println("Reject Reserve Room Server Request " + reserveRoomServerRequest);
                reserveRoomServerService.sendResponse(
                        new ReserveRoomServerResponse(reserveRoomServerRequest.getRoomId(), false), ctx.channel());
            }


        } else {
            ctx.fireChannelRead(msg);
        }

    }
}
