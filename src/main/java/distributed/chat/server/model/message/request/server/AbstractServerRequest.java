package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public abstract class AbstractServerRequest extends AbstractMessage {
    public AbstractServerRequest(MessageType type) {
        super(type);
    }
}
