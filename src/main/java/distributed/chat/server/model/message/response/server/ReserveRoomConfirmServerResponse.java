package distributed.chat.server.model.message.response.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class ReserveRoomConfirmServerResponse extends AbstractServerResponse{

    private final String roomId;
    private final boolean reserved;

    public ReserveRoomConfirmServerResponse(String roomId, boolean reserved) {
        // {"type" : "reserveroomconfirmresponse", "roomid" : "jokes", "reserved" : "true"}
        super(MessageType.RESERVE_ROOM_CONFIRM_RESPONSE);
        this.roomId = roomId;
        this.reserved = reserved;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isReserved() {
        return reserved;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.RESERVE_ROOM_CONFIRM_RESPONSE + '"' +
                ", \"roomid\" : \"" + roomId + '"' +
                ", \"reserved\" : \"" + reserved + '"' +
                '}';
    }
}
