package distributed.chat.server.model.message.response.client;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class AbstractClientResponse extends AbstractMessage {

    public AbstractClientResponse(MessageType type) {
        super(type);
    }
}
