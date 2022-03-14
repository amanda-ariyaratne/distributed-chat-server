package distributed.chat.server.service.client;

import com.google.gson.Gson;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.states.ServerState;
import io.netty.channel.*;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;

import java.util.Map;

public abstract class AbstractClientService<S extends AbstractClientRequest, T extends AbstractClientResponse> {

    public abstract void processRequest(S request);

    public void broadcast(S request, Room room) {
        Gson gson = new Gson();
        String requestJsonStr = gson.toJson(request);

        for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
            final ChannelFuture f = ServerState.serverChannels.get(server.getKey()).writeAndFlush(requestJsonStr + "\n");
            f.addListener((ChannelFutureListener) future -> {
                assert f == future;
            });
        }
    }

    public void sendResponse(T response, Client client) {
        Gson gson = new Gson();
        String responseJsonStr = gson.toJson(response);

        final ChannelFuture f = client.getCtx().writeAndFlush(responseJsonStr + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }
}
