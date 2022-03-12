package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.MessageType;

public class ReserveRoomServerRequest extends AbstractServerRequest {

    private String serverId;
    private String roomId;

    public ReserveRoomServerRequest(String serverId, String roomId) {
        super(MessageType.RESERVE_ROOM);
        this.serverId = serverId;
        this.roomId = roomId;
    }

}
