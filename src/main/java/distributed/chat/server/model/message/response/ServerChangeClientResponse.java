package distributed.chat.server.model.message.response;

import distributed.chat.server.model.message.AbstractClientResponse;
import distributed.chat.server.model.message.MessageType;

public class ServerChangeClientResponse extends AbstractClientResponse {

    private boolean approved;
    private String serverId;

    public ServerChangeClientResponse(boolean approved, String serverId) {
        super(MessageType.SERVER_CHANGE);
        this.approved = approved;
        this.serverId = serverId;
    }

}
