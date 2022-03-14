package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class NominationMessage extends AbstractMessage {

    private String serverId;

    public NominationMessage(String serverId) {
        super(MessageType.NOMINATION);
        this.serverId = serverId;
    }
}
