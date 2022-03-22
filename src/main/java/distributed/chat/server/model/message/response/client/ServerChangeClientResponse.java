package distributed.chat.server.model.message.response.client;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class ServerChangeClientResponse extends AbstractClientResponse {

    private boolean approved;
    private String serverId;

    public ServerChangeClientResponse(boolean approved, String serverId) {
        super(MessageType.SERVER_CHANGE);
        this.approved = approved;
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.SERVER_CHANGE + '"' +
                ", \"approved\" : \"" + approved + '"' +
                ", \"serverid\" : \"" + serverId + '"' +
                '}';
    }
}
