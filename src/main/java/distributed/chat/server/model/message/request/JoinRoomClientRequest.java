package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class JoinRoomClientRequest extends AbstractClientRequest {

    private String roomId;

    public JoinRoomClientRequest(String roomId) {
        super(MessageType.JOIN_ROOM);
        this.roomId = roomId;
    }
}
