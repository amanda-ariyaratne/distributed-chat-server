package distributed.chat.server.model.message.election;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class AnswerMessage extends FastBullyMessage {

    private String serverId;

    public AnswerMessage(String serverId) {
        super(MessageType.ANSWER);
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.ANSWER + "\", " +
                "\"serverid\" : \"" + serverId + '\"' +
                '}';
    }
}
