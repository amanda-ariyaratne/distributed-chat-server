package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.QuitClientRequest;
import distributed.chat.server.service.client.QuitService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;

import java.net.SocketException;
import java.util.Map;

/***
 * Inbound Handler for handling exceptions
 */
public class ClientExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("[" + ServerState.localId + " WARN]: Channel Inactive event detected");
        for (Map.Entry<String, Client> client : ServerState.localClients.entrySet()) {
            Channel c = client.getValue().getCtx().channel();

            QuitClientRequest quitter = new QuitClientRequest();
            quitter.setSender(client.getValue());

            if (c.equals(ctx.channel())) {
                QuitService.getInstance().processRequest(quitter);
                break;
            }
        }
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof SocketException) {
            ctx.close();
        } else if (cause instanceof CorruptedFrameException) {}
//        else {
//            cause.printStackTrace();
//        }
    }
}