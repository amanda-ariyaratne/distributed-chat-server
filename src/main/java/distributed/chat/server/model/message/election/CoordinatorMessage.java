package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class CoordinatorMessage extends AbstractMessage {

    private String serverId;

    public CoordinatorMessage(String serverId) {
        super(MessageType.COORDINATOR);
        this.serverId = serverId;
    }
}
