package distributed.chat.server.model.message.response.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public abstract class AbstractServerResponse extends AbstractMessage {

    public AbstractServerResponse(MessageType type) {
        super(type);
    }
}