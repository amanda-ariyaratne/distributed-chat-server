package distributed.chat.server.model.message.response.client;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class RoomChangeClientResponse extends AbstractClientResponse{
    private final String identity;
    private final String former;
    private final String roomId;

    public RoomChangeClientResponse(String identity, String former, String roomId) {
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

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.ROOM_CHANGE + '"' +
                ", \"identity\" : \"" + identity + '"' +
                ", \"former\" : \"" + former + '"' +
                ", \"roomid\" : \"" + roomId + '"' +
                '}';
    }
}
