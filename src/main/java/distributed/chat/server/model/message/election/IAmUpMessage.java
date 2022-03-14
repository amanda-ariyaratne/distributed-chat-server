package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class IAmUpMessage extends AbstractMessage {

    private String serverId;

    public IAmUpMessage(String serverId) {
        super(MessageType.I_AM_UP);
        this.serverId = serverId;
    }
}
