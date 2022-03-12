package distributed.chat.server.model.message.response.client;

import distributed.chat.server.model.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class RoomListClientResponse extends AbstractClientResponse {

    private List<String> rooms;

    public RoomListClientResponse() {
        super(MessageType.ROOM_LIST);
        this.rooms = new ArrayList<>();
    }

    public RoomListClientResponse(ArrayList<String> rooms) {
        super(MessageType.ROOM_LIST);
        this.rooms = rooms;
    }

    public void addRoom(String roomId) {
        this.rooms.add(roomId);
    }
}
