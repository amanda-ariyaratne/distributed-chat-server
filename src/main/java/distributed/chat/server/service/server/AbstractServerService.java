package distributed.chat.server.service.server;

import com.google.gson.Gson;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public abstract class AbstractServerService <S extends AbstractServerRequest, T extends AbstractClientResponse> {

    public abstract T processRequest(S request, Channel channel);

    public void sendRequest(S request, Channel channel) {
        Gson gson = new Gson();
        String responseJsonStr = gson.toJson(request);
        final ChannelFuture f = channel.writeAndFlush(responseJsonStr + "\n");
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
        });
    }
}
