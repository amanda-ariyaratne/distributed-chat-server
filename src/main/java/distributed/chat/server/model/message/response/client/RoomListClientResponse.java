package distributed.chat.server.model.message.response.client;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class RoomListClientResponse extends AbstractClientResponse {
    //{
    //"type" : "roomlist",
    //"rooms" : ["MainHall-s1", "MainHall-s2", "jokes"]
    //}

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

    @Override
    public String toString() {
        String roomsStr = "";
        for (int i = 0; i < rooms.size(); i++) {
            if (i == rooms.size() - 1) {
                roomsStr.concat("'" + rooms.get(i) + "'");
            } else {
                roomsStr.concat("'" + rooms.get(i) + "',");
            }
        }
        return "{" +
                "\"type\" : \"" + RequestConstants.ROOM_LIST + '"' +
                ", \"rooms\" : [" + roomsStr + "]" +
                '}';
    }
}
