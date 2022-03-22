package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class JoinRoomClientRequest extends AbstractClientRequest {

    private final String roomId;
    private Client sender;

    public JoinRoomClientRequest(String roomId) {
        // {"type" : "joinroom", "roomid" : "jokes"}
        super(MessageType.JOIN_ROOM);
        this.roomId = roomId;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "{" +
                " \"type\" : \"" + MessageType.JOIN_ROOM + '\"' +
                ", \"roomId\" : \"" + roomId + '\"' +
                '}';
    }
}
