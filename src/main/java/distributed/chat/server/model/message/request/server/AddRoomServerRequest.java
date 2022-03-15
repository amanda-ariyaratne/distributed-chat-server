package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.MessageType;

/***
 * Request obj to broadcast to all the servers when a new room is created
 */
public class AddRoomServerRequest extends AbstractServerRequest {

    private final String roomId;

    public AddRoomServerRequest(String roomId) {
        // {"type" : "addroom", "roomid" : "jokes"}
        super(MessageType.ADD_ROOM);
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.ADD_ROOM + '\'' +
                ", roomId:'" + roomId + '\'' +
                '}';
    }
}
