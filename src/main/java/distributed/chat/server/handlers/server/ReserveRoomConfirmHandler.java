package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.request.server.ReserveRoomConfirmServerRequest;
import distributed.chat.server.model.message.response.server.AbstractServerResponse;
import distributed.chat.server.model.message.response.server.ReserveRoomServerResponse;
import distributed.chat.server.service.server.ReservedRoomConfirmServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ReserveRoomConfirmHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractServerResponse abstractServerResponse = (AbstractServerResponse) msg;

        if (abstractServerResponse instanceof ReserveRoomServerResponse) {
            // request object
            ReserveRoomServerResponse response = (ReserveRoomServerResponse) abstractServerResponse;
            // get service
            ReservedRoomConfirmServerService reservedRoomConfirmServerService = ReservedRoomConfirmServerService.getInstance();
            // call service
            reservedRoomConfirmServerService.processRequest(
                    new ReserveRoomConfirmServerRequest(response.getRoomId(), response.isReserved()), ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
