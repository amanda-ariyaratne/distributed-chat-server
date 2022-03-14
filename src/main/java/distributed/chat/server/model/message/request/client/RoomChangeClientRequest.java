package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;

public class RoomChangeClientRequest extends AbstractClientRequest {

    private final String identity;
    private final String former;
    private final String roomId;

    public RoomChangeClientRequest(String identity, String former, String roomId) {
        super(MessageType.ROOM_CHANGE);
        this.identity = identity;
        this.former = former;
        this.roomId = roomId;
    }

    public String getIdentity() {
        return identity;
    }

    public String getFormer() {
        return former;
    }

    public String getRoomId() {
        return roomId;
    }
}
