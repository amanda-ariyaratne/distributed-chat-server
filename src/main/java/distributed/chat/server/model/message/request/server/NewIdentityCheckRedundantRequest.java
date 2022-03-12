package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.MessageType;

public class NewIdentityCheckRedundantRequest extends AbstractServerRequest {
    private String identity;

    public NewIdentityCheckRedundantRequest(String identity) {
        // Todo: new identity redundant check message type
        super(MessageType.NEW_IDENTITY);
        this.identity = identity;
    }
}
