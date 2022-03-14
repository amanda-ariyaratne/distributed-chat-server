package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.MessageType;

public class ReserveRoomServerRequest extends AbstractServerRequest {

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
}
