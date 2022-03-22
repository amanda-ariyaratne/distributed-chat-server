package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class MessageClientRequest extends AbstractClientRequest {

    private final String content;
    private Client sender;

    public MessageClientRequest(String content) {
        super(MessageType.MESSAGE);
        this.content = content;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public Client getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.MESSAGE + '\'' +
                ", content:'" + content + '\'' +
                '}';
    }
}
