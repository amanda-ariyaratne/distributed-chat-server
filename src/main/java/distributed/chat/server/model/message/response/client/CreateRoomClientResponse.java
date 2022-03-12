package distributed.chat.server.model.message.response.client;

import distributed.chat.server.model.message.MessageType;

public class CreateRoomClientResponse extends AbstractClientResponse {

    private String roomId;
    private boolean approved;

    public CreateRoomClientResponse(String roomId, boolean approved) {
        super(MessageType.CREATE_ROOM);
        this.roomId = roomId;
        this.approved = approved;
    }

}
