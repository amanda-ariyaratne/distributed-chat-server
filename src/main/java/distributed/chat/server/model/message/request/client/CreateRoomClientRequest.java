package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class CreateRoomClientRequest extends AbstractClientRequest {

    private String roomId;

    public CreateRoomClientRequest(String roomId) {
        super(MessageType.CREATE_ROOM);
        this.roomId = roomId;
    }

}
