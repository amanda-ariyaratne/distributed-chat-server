package distributed.chat.server.model.message.election;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class AnswerMessage extends AbstractMessage {

    private String serverId;

    public AnswerMessage(String serverId) {
        super(MessageType.ANSWER);
        this.serverId = serverId;
    }
}
