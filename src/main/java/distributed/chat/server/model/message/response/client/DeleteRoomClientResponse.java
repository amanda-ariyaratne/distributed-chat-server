package distributed.chat.server.model.message.response.client;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class DeleteRoomClientResponse extends AbstractClientResponse {

    private final String roomId;
    private final boolean approved;

    public DeleteRoomClientResponse(String roomId, boolean approved) {
        super(MessageType.DELETE_ROOM);
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
                "\"type\":\"" + RequestConstants.DELETE_ROOM + '"' +
                ", \"roomid\":\"" + roomId + '"' +
                ", \"approved\":\"" + approved + '"' +
                '}';
    }
}
