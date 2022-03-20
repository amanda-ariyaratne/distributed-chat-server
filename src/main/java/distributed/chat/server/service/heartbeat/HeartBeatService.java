package distributed.chat.server.service.heartbeat;

import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class HeartBeatService {

    public static void removeConnection(String serverId) {
        Channel channel = ServerState.serverChannels.get(serverId);
        channel.close();
        ServerState.serverChannels.remove(serverId);
    }
}
