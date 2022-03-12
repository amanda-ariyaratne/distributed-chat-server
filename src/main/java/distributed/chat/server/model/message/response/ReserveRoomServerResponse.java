package distributed.chat.server.model.message.response;

import distributed.chat.server.model.message.AbstractServerResponse;
import distributed.chat.server.model.message.MessageType;

public class ReserveRoomServerResponse extends AbstractServerResponse {

    private String serverId;
    private String roomId;
    private boolean reserved;

    public ReserveRoomServerResponse(String serverId, String roomId, boolean reserved) {
        super(MessageType.RESERVE_ROOM);
        this.serverId = serverId;
        this.roomId = roomId;
        this.reserved = reserved;
    }

}
