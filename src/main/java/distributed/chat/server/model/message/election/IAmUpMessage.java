package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.MessageType;

public class IAmUpMessage extends FastBullyMessage {

    private String serverId;

    public IAmUpMessage(String serverId) {
        super(MessageType.I_AM_UP);
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'iamup'" +
                "serverId:'" + serverId + '\'' +
                '}';
    }
}
