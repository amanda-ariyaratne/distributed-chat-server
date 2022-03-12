package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.MessageType;

public class DeleteIdentityServerRequest extends AbstractServerRequest {

    private String serverId;
    private String identity;

    public DeleteIdentityServerRequest(String serverId, String identity) {
        super(MessageType.DELETE_IDENTITY);
        this.serverId = serverId;
        this.identity = identity;
    }

}
