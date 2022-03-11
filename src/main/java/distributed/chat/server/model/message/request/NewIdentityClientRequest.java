package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class NewIdentityClientRequest extends AbstractClientRequest {

    private String identity;

    public NewIdentityClientRequest(String identity) {
        super(MessageType.NEW_IDENTITY);
        this.identity = identity;
    }

}
