package distributed.chat.server.model.message.request.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class AddIdentityServerRequest extends AbstractServerRequest {

    private final String identity;

    public AddIdentityServerRequest(String identity) {
        super(MessageType.ADD_IDENTITY);
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + RequestConstants.ADD_IDENTITY + '"' +
                ", \"identity\":\"" + identity + '"' +
                '}';
    }
}
