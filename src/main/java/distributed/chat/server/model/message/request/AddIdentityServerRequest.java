package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.MessageType;

public class AddIdentityServerRequest extends AbstractServerRequest {

    private String serverId;
    private String identity;

    public AddIdentityServerRequest(String serverId, String identity) {
        super(MessageType.ADD_IDENTITY);
        this.serverId = serverId;
        this.identity = identity;
    }

}
