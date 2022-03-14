package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;

public class MessageClientRequest extends AbstractClientRequest {

    String content;

    public MessageClientRequest(String content) {
        super(MessageType.MESSAGE);
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.MESSAGE + '\'' +
                ", content:'" + content + '\'' +
                '}';
    }
}
