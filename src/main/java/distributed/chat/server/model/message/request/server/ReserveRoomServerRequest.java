package distributed.chat.server.model.message.request.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

/***
 * Request obj sent from slave to leader to reserve the new roomId
 */
public class ReserveRoomServerRequest extends AbstractServerRequest {

    private final String serverId;
    private final String roomId;

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

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.RESERVE_ROOM + '"' +
                ", \"serverid\" : \"" + serverId + '"' +
                ", \"roomid\" : \"" + roomId + '"' +
                '}';
    }
}
