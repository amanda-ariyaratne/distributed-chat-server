package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class QuitClientRequest extends AbstractClientRequest {

    private Client sender;

    public QuitClientRequest() {
        super(MessageType.QUIT);
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
                "type:'" + MessageType.QUIT + '\'' +
                "}";
    }
}
