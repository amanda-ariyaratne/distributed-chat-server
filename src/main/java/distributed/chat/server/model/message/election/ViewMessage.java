package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.MessageType;

public class ViewMessage extends FastBullyMessage {

    private String serverId;
    private String currentLeaderId;

    public ViewMessage(String serverId, String currentLeaderId) {
        super(MessageType.VIEW);
        this.serverId = serverId;
        this.currentLeaderId = currentLeaderId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getCurrentLeaderId() {
        return currentLeaderId;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'view'" +
                "serverId:'" + serverId + '\'' +
                "currentLeaderId:'" + currentLeaderId + '\'' +
                '}';
    }
}
