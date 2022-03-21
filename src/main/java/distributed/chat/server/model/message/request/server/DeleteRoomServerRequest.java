package distributed.chat.server.model.message.request.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public class DeleteRoomServerRequest extends AbstractMessage {

    private final String serverId;
    private final String roomId;

    public DeleteRoomServerRequest(String serverId, String roomId) {
        super(MessageType.DELETE_ROOM);
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
                "type:'" + RequestConstants.DELETE_ROOM + '\'' +
                ", serverId:'" + serverId + '\'' +
                ", roomId:'" + roomId + '\'' +
                '}';
    }
}
