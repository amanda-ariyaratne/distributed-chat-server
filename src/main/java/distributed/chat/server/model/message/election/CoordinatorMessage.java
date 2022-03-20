package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class CoordinatorMessage extends FastBullyMessage {

    private String serverId;

    public CoordinatorMessage(String serverId) {
        super(MessageType.COORDINATOR);
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'coordinator'" +
                "serverId:'" + serverId + '\'' +
                '}';
    }
}
