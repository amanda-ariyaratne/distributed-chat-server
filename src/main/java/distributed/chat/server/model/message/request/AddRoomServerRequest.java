package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.MessageType;

public class AddRoomServerRequest extends AbstractServerRequest {

    private String serverId;
    private String roomId;

    public AddRoomServerRequest(String serverId, String roomId) {
        super(MessageType.ADD_ROOM);
        this.serverId = serverId;
        this.roomId = roomId;

    }
}
