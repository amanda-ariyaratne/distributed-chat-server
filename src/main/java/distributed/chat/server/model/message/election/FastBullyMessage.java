package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class FastBullyMessage extends AbstractMessage {
    public FastBullyMessage(MessageType type) {
        super(type);
    }
}
