package distributed.chat.server.handlers.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.request.client.QuitClientRequest;
import distributed.chat.server.service.client.QuitService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class QuitHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // {"type":"quit"}
        AbstractClientRequest request = (AbstractClientRequest) msg;
        if (request instanceof QuitClientRequest){
            Client client = ServerState.activeClients.get(ctx.channel().id());

            QuitClientRequest quitClientRequest = (QuitClientRequest) msg;
            quitClientRequest.setSender(client);

            QuitService.getInstance().processRequest(quitClientRequest);

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}