package distributed.chat.server.service.client;

import com.google.gson.Gson;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public abstract class AbstractClientService<S extends AbstractClientRequest, T extends AbstractClientResponse> {

    public abstract void processRequest(S request);

    public void broadcast(S request, Room room) {
        Gson gson = new Gson();
        String requestJsonStr = gson.toJson(request);

        for (Client member : room.getMembers()) {
            final ChannelFuture f = member.getCtx().writeAndFlush(requestJsonStr + "\n");
            f.addListener((ChannelFutureListener) future -> {
                assert f == future;
            });
        }
    }

    public void broadcast(T response, Room room) {
        System.out.println("Member length " + room.getMembers().size());
        System.out.println(response.toString());
        for (Client member : room.getMembers()) {
            final ChannelFuture f = member.getCtx().writeAndFlush(response + "\n");
            f.addListener((ChannelFutureListener) future -> {
                assert f == future;
            });
        }
        System.out.println("finish broadcast");
    }

    public void sendResponse(T response, Client client) {
        final ChannelFuture f = client.getCtx().writeAndFlush(response + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }
}
