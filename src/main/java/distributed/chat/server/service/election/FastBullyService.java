package distributed.chat.server.service.election;

import distributed.chat.server.model.message.election.FastBullyMessage;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.Map;

public abstract class FastBullyService<S extends FastBullyMessage> {

    public abstract void processMessage(S message, Channel channel);

    public void broadcast(S message) {
        for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
            final ChannelFuture f = ServerState.serverChannels.get(server.getKey()).writeAndFlush(message.toString());
            f.addListener((ChannelFutureListener) future -> {
                assert f == future;
            });
        }
    }

}
