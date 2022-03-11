package distributed.chat.server.service;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractClientRequest;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.json.simple.JSONObject;

public abstract class AbstractClientService {

    public abstract void processRequest(AbstractClientRequest request);
    public abstract void handleRequest(AbstractClientRequest request);

    public void sendResponse(JSONObject reply, Client client) {
        final ChannelFuture f = client.getCtx().writeAndFlush(reply.toJSONString() + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }

}
