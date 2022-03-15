package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class CreateRoomClientRequest extends AbstractClientRequest {

    private String roomId;
    private Client sender;

    public CreateRoomClientRequest(String roomId) {
        // {"type" : "createroom", "roomid" : "jokes"}
        super(MessageType.CREATE_ROOM);
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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
                "type:'" + MessageType.CREATE_ROOM + '\'' +
                ", roomId:'" + roomId + '\'' +
                '}';

    }
}
