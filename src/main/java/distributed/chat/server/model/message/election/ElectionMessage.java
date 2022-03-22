package distributed.chat.server.model.message.election;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class ElectionMessage extends FastBullyMessage {

    private String serverId;

    public ElectionMessage(String serverId) {
        super(MessageType.ELECTION);
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.ELECTION + "\", " +
                "\"serverid\" : \"" + serverId + '\"' +
                '}';
    }
}
