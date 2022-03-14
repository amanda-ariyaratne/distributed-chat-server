package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class ElectionMessage extends AbstractMessage {

    private String serverId;

    public ElectionMessage(String serverId) {
        super(MessageType.ELECTION);
        this.serverId = serverId;
    }
}
