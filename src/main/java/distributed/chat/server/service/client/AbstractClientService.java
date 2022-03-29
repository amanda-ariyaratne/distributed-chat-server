package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public abstract class AbstractClientService<S extends AbstractClientRequest, T extends AbstractClientResponse> {

    public abstract void processRequest(S request);

    public void broadcast(S request, Room room) {
        System.out.println("INFO: Broadcast Request: " + request.toString());
        for (Client member : room.getMembers()) {
            final ChannelFuture f = member.getCtx().writeAndFlush(request.toString() + "\n");
            f.addListener((ChannelFutureListener) future -> {
                assert f == future;
            });
        }
    }

    public void broadcast(T response, Room room) {
        System.out.println("INFO: Broadcast Response: " + response.toString());
        for (Client member : room.getMembers()) {
            final ChannelFuture f = member.getCtx().writeAndFlush(response.toString() + "\n");
            f.addListener((ChannelFutureListener) future -> {
                assert f == future;
            });
        }
    }

    public void sendResponse(T response, Client client) {
        System.out.println("INFO: Response: " + response.toString());
        final ChannelFuture f = client.getCtx().writeAndFlush(response.toString() + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }
}
