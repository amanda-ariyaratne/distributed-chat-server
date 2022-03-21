package distributed.chat.server.model.message.heartbeat;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class HeartBeatMessage extends AbstractMessage {

    public HeartBeatMessage() {
        super(MessageType.HEARTBEAT);
    }

    @Override
    public String toString() {
        return "{type: '" + RequestConstants.HEARTBEAT + "'}";
    }
}
