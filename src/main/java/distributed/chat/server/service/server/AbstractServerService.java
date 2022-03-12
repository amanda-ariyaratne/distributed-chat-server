package distributed.chat.server.service.server;

import com.google.gson.Gson;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.AbstractClientRequest;
import distributed.chat.server.model.message.AbstractClientResponse;
import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.AbstractServerResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public abstract class AbstractServerService <S extends AbstractServerRequest> {

    public abstract void processRequest(S request, Channel channel);

    public void sendRequest(S request, Channel channel) {
        Gson gson = new Gson();
        String responseJsonStr = gson.toJson(request);
        final ChannelFuture f = channel.writeAndFlush(responseJsonStr + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }
}
