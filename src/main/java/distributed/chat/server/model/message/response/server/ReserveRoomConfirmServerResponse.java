package distributed.chat.server.model.message.response.server;

import distributed.chat.server.model.message.MessageType;

public class ReserveRoomConfirmServerResponse extends AbstractServerResponse{


    private final String roomId;
    private final boolean reserved;

    public ReserveRoomConfirmServerResponse(String roomId, boolean reserved) {
        super(MessageType.RESERVE_ROOM_CONFIRM);
        this.roomId = roomId;
        this.reserved = reserved;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isReserved() {
        return reserved;
    }
}
