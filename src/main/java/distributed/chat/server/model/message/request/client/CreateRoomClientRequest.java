package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;

public class CreateRoomClientRequest extends AbstractClientRequest {

    private final String roomId;

    public CreateRoomClientRequest(String roomId) {
        super(MessageType.CREATE_ROOM);
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.CREATE_ROOM + '\'' +
                ", roomId:'" + roomId + '\'' +
                '}';
    }
}
