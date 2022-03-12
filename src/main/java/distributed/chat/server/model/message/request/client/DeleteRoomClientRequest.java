package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;

public class DeleteRoomClientRequest extends AbstractClientRequest {

    private String roomId;

    public DeleteRoomClientRequest(String roomId) {
        super(MessageType.DELETE_ROOM);
        this.roomId = roomId;
    }

}
