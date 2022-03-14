package distributed.chat.server.service.server;

import com.google.gson.Gson;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.Map;

public abstract class AbstractServerService <S extends AbstractServerRequest> {

    public abstract void processRequest(S request, Channel channel);

    public void broadcast(S request) {
        Gson gson = new Gson();
        String requestJsonStr = gson.toJson(request);

        for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
            final ChannelFuture f = ServerState.serverChannels.get(server.getKey()).writeAndFlush(requestJsonStr + "\n");
            f.addListener((ChannelFutureListener) future -> {
                assert f == future;
            });
        }
    }

    public void sendRequest(S request, Channel channel) {
        Gson gson = new Gson();
        String responseJsonStr = gson.toJson(request);
        final ChannelFuture f = channel.writeAndFlush(responseJsonStr + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }
}
