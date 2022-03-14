package distributed.chat.server.model.message.response.server;

import distributed.chat.server.model.message.AbstractServerResponse;
import distributed.chat.server.model.message.MessageType;

public class ReserveRoomServerResponse extends AbstractServerResponse {

    private final String serverId;
    private final String roomId;
    private final boolean reserved;

    public ReserveRoomServerResponse(String serverId, String roomId, boolean reserved) {
        super(MessageType.RESERVE_ROOM);
        this.serverId = serverId;
        this.roomId = roomId;
        this.reserved = reserved;
    }

    public String getServerId() {
        return serverId;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isReserved() {
        return reserved;
    }
}
