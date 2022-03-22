package distributed.chat.server.handlers.server;

import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.DeleteRoomServerRequest;
import distributed.chat.server.service.server.DeleteRoomServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DeleteRoomServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractServerRequest abstractServerRequest = (AbstractServerRequest) msg;

        if (abstractServerRequest instanceof DeleteRoomServerRequest){
            DeleteRoomServerRequest request = (DeleteRoomServerRequest) abstractServerRequest;

            System.out.println("DeleteRoomServerHandler Request : "+request);
            DeleteRoomServerService.getInstance().processRequest(request, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
