package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class ReserveRoomServerRequest extends AbstractMessage {

    private final String serverId;
    private final String roomId;

    public ReserveRoomServerRequest(String serverId, String roomId) {
        super(MessageType.RESERVE_ROOM);
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
                "type:'" + MessageType.RESERVE_ROOM + '\'' +
                ", serverId:'" + serverId + '\'' +
                ", roomId:'" + roomId + '\'' +
                '}';
    }
}
