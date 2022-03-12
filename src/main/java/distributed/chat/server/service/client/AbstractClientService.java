package distributed.chat.server.service.client;

import com.google.gson.Gson;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;

public abstract class AbstractClientService<S extends AbstractClientRequest, T extends AbstractClientResponse> {

    public abstract T processRequest(S request);

    public void sendResponse(T response, Client client) {
        Gson gson = new Gson();
        String responseJsonStr = gson.toJson(response);
        final ChannelFuture f = client.getCtx().writeAndFlush(responseJsonStr + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }
}
