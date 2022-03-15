package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class AddRoomServerRequest extends AbstractServerRequest {

    private final String serverId;
    private final String roomId;

    public AddRoomServerRequest(String serverId, String roomId) {
        super(MessageType.ADD_ROOM);
        this.serverId = serverId;
        this.roomId = roomId;

    }

    public String getServerId() {
        return serverId;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.ADD_ROOM + '\'' +
                ", serverId:'" + serverId + '\'' +
                ", roomId:'" + roomId + '\'' +
                '}';
    }
}
