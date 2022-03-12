package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class MessageClientRequest extends AbstractClientRequest {

    String content;

    public MessageClientRequest(String content) {
        super(MessageType.MESSAGE);
        this.content = content;
    }
}
