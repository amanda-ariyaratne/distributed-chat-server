package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.server.ReserveRoomConfirmServerRequest;
import distributed.chat.server.model.message.response.server.AbstractServerResponse;
import distributed.chat.server.model.message.response.server.ReserveRoomServerResponse;
import distributed.chat.server.service.server.ReservedRoomConfirmServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 * Reserve Room Confirm Inbound Handler
 * Handle responses sent from leader to slave
 */
public class ReserveRoomConfirmHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage abstractServerResponse = (AbstractMessage) msg;

        if (abstractServerResponse instanceof ReserveRoomServerResponse) {
            // request object : {"type" : "reserveroomresponse", "roomid" : "jokes", "reserved" : "true"}
            ReserveRoomServerResponse response = (ReserveRoomServerResponse) abstractServerResponse;
            System.out.println("INFO: Received Rseserve Room Server Response " + response);
            // get service
            ReservedRoomConfirmServerService reservedRoomConfirmServerService = ReservedRoomConfirmServerService.getInstance();

            System.out.println("INFO: " + "recieved reserve request for room " + response.getRoomId());
            reservedRoomConfirmServerService.processRequest(
                    new ReserveRoomConfirmServerRequest(response.getRoomId(), response.isReserved()), ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
