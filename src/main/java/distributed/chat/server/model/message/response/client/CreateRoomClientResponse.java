package distributed.chat.server.model.message.response.client;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class CreateRoomClientResponse extends AbstractClientResponse {

    private final String roomId;
    private final boolean approved;

    public CreateRoomClientResponse(String roomId, boolean approved) {
        // {"type" : "createroom", "roomid" : "jokes", "approved" : "true"}
        super(MessageType.CREATE_ROOM);
        this.roomId = roomId;
        this.approved = approved;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isApproved() {
        return approved;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + RequestConstants.CREATE_ROOM + '"' +
                ", \"roomid\":\"" + roomId + '"' +
                ", \"approved\":\"" + approved + '"' +
                '}';
    }
}
