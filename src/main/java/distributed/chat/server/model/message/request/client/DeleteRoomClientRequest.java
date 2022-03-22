package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class DeleteRoomClientRequest extends AbstractClientRequest {

    private final String roomId;
    private Client sender;

    public DeleteRoomClientRequest(String roomId) {
        super(MessageType.DELETE_ROOM);
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + MessageType.DELETE_ROOM + '\"' +
                ", \"roomId\" : \"" + roomId + '\"' +
                '}';
    }
}
