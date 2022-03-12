package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class NewIdentityClientRequest extends AbstractClientRequest {

    private String identity;
    private Client sender;

    public NewIdentityClientRequest(String identity) {
        super(MessageType.NEW_IDENTITY);
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }
}
