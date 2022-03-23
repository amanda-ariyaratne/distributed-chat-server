package distributed.chat.server.model.message.election;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class NominationMessage extends FastBullyMessage {

    private String serverId;

    public NominationMessage(String serverId) {
        super(MessageType.NOMINATION);
        this.serverId = serverId;
    }


    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.NOMINATION + "\", " +
                "\"serverid\" : \"" + serverId + '\"' +
                '}';
    }
}
