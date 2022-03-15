package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class NewIdentityClientRequest extends AbstractClientRequest {

    private final String identity;
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

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.NEW_IDENTITY + '\'' +
                ", identity:'" + identity + '\'' +
                ", sender:" + sender +
                '}';
    }
}
