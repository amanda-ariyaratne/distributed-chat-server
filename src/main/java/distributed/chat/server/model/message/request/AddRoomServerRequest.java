package distributed.chat.server.model.message.request;


import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;

public class AddRoomServerRequest extends AbstractServerRequest {

    private String serverId;
    private String roomId;

    public AddRoomServerRequest(String serverId, String roomId) {
        super(MessageType.ADD_ROOM);
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
