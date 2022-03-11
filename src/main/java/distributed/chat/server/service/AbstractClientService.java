package distributed.chat.server.service;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractClientRequest;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.json.simple.JSONObject;
import distributed.chat.server.model.message.AbstractClientResponse;

public abstract class AbstractClientService<S extends AbstractClientRequest, T extends AbstractClientResponse> {

    public abstract T processRequest(S request);

    public void sendResponse(JSONObject reply, Client client) {
        final ChannelFuture f = client.getCtx().writeAndFlush(reply.toJSONString() + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }

}
