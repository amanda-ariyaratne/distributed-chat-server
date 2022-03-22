package distributed.chat.server.model.message.request.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

/***
 * Request obj to broadcast to all the servers when a new room is created
 */
public class AddRoomServerRequest extends AbstractServerRequest {

    private final String roomId;
    private final String serverId;

    public AddRoomServerRequest(String roomId, String serverId) {
        // {"type" : "addroom", "serverid" : "s1", "roomid" : "jokes"}
        super(MessageType.ADD_ROOM);
        this.roomId = roomId;
        this.serverId = serverId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getServerId() {
        return serverId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.ADD_ROOM + '\"' +
                ", \"roomId\" : \"" + roomId + '\"' +
                '}';
    }
}
