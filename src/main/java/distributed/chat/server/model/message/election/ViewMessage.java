package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class ViewMessage extends AbstractMessage {

    private String serverId;
    private String currentLeaderId;

    public ViewMessage(String serverId, String currentLeaderId) {
        super(MessageType.VIEW);
        this.serverId = serverId;
        this.currentLeaderId = currentLeaderId;
    }

}
