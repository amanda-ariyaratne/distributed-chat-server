package distributed.chat.server.model.message.response.server;

import distributed.chat.server.model.message.MessageType;

/***
 * Response sent from leader to slave
 */
public class ReserveRoomServerResponse extends AbstractServerResponse {

    private final String roomId;
    private final boolean reserved;

    public ReserveRoomServerResponse(String roomId, boolean reserved) {
        // {"type" : "reserveroomresponse", "roomid" : "jokes", "reserved" : "true"}
        super(MessageType.RESERVE_ROOM_RESPONSE);
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
                "type:'" + MessageType.RESERVE_ROOM_RESPONSE + '\'' +
                ", roomId='" + roomId + '\'' +
                ", reserved=" + reserved +
                '}';
    }
}
