package distributed.chat.server.model.message.response.client;

import distributed.chat.server.model.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class RoomContentsClientResponse extends AbstractClientResponse {

    private final String roomId;
    private final List<String> identities;
    private final String owner;

    public RoomContentsClientResponse(String roomId, ArrayList<String> identities, String owner) {
        super(MessageType.ROOM_CONTENTS);
        this.roomId = roomId;
        this.identities = identities;
        this.owner = owner;
    }

    public String getRoomId() {
        return roomId;
    }

    public List<String> getIdentities() {
        return identities;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.ROOM_CONTENTS + '\'' +
                ", roomid:'" + roomId + '\'' +
                ", identities:" + identities +
                ", owner:'" + owner + '\'' +
                '}';
    }
}
