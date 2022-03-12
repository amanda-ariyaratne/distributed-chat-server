package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.MessageType;

public class DeleteRoomServerRequest extends AbstractServerRequest {

    private String serverId;
    private String roomId;

    public DeleteRoomServerRequest(String serverId, String roomId) {
        super(MessageType.DELETE_ROOM);
        this.serverId = serverId;
        this.roomId = roomId;
    }

}
