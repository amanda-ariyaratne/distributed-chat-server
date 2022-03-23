package distributed.chat.server.model.message.heartbeat;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class HeartBeatMessage extends AbstractMessage {

    private String serverId;

    public HeartBeatMessage(String serverId)
    {
        super(MessageType.HEARTBEAT);
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "{\"type\" : \"" + RequestConstants.HEARTBEAT + "\", " +
                "\"serverid\" : \"" + serverId + "\"" +
                "}";
    }
}
