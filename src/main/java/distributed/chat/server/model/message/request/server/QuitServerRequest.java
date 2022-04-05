package distributed.chat.server.model.message.request.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class QuitServerRequest extends AbstractServerRequest {

    private final String identity;
    private final String roomId;

    public QuitServerRequest(String identity, String roomId) {
        //{"type" : "quit", "identity" : "name"}
        super(MessageType.QUIT_SERVER);
        this.identity = identity;
        this.roomId = roomId;
    }

    public String getIdentity() {
        return identity;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.QUIT_SERVER + '"' +
                ", \"identity\" : \"" + identity + '"' +
                ", \"roomid\" : \"" + roomId + '"' +
                "}";
    }
}
