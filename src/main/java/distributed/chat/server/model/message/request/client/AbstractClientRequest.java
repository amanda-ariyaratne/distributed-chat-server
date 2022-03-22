package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public abstract class AbstractClientRequest extends AbstractMessage {

    public AbstractClientRequest(MessageType type) {
        super(type);
    }
}
