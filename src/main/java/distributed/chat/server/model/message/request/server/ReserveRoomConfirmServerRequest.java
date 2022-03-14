package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.MessageType;

public class ReserveRoomConfirmServerRequest extends AbstractServerRequest{

    private final String roomId;
    private final boolean reserved;

    public ReserveRoomConfirmServerRequest(String roomId, boolean reserved) {
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
