package distributed.chat.server.model.message.response.client;

import distributed.chat.server.model.message.MessageType;

public class RoomChangeClientResponse extends AbstractClientResponse {

    private String identity;
    private String former;
    private String roomId;

    public RoomChangeClientResponse(String identity, String former, String roomId) {
        super(MessageType.ROOM_CHANGE);
        this.identity = identity;
        this.former = former;
        this.roomId = roomId;
    }

}
