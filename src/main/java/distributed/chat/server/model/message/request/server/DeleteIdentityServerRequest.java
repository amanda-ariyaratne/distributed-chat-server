package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class DeleteIdentityServerRequest extends AbstractMessage {

    private final String serverId;
    private final String identity;

    public DeleteIdentityServerRequest(String serverId, String identity) {
        super(MessageType.DELETE_IDENTITY);
        this.serverId = serverId;
        this.identity = identity;
    }

    public String getServerId() {
        return serverId;
    }

    public String getIdentity() {
        return identity;
    }
}
