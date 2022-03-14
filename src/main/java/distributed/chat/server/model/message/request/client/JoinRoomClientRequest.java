package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;

public class JoinRoomClientRequest extends AbstractClientRequest {

    private final String roomId;

    public JoinRoomClientRequest(String roomId) {
        super(MessageType.JOIN_ROOM);
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.JOIN_ROOM + '\'' +
                ", roomId:'" + roomId + '\'' +
                '}';
    }
}
