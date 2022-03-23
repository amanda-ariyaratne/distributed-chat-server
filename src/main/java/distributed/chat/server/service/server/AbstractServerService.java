package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.response.server.AbstractServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.Map;

public abstract class AbstractServerService<S extends AbstractServerRequest, T extends AbstractServerResponse> {

    public abstract void processRequest(S request, Channel channel);

    public void broadcast(S request) {
        System.out.println(request.toString());
        for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
            System.out.println("Broadcast to : " + server.getKey());
            final ChannelFuture f = ServerState.serverChannels.get(server.getKey()).writeAndFlush(request.toString());
            f.addListener((ChannelFutureListener) future -> {
                assert f == future;
            });
        }
    }

    public void sendRequest(S request, Channel channel) {
        final ChannelFuture f = channel.writeAndFlush(request.toString());
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }

    public void sendResponse(T response, Channel channel) {
        final ChannelFuture f = channel.writeAndFlush(response.toString());
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }
}
