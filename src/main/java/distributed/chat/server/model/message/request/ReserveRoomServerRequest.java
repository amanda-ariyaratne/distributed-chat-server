package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;

public class ReserveRoomServerRequest extends AbstractServerRequest {

    private String serverId;
    private String roomId;

    public ReserveRoomServerRequest(String serverId, String roomId) {
        // {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}
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
