package distributed.chat.server.model.message.response.client;

import distributed.chat.server.model.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class RoomContentsClientResponse extends AbstractClientResponse {

    private String roomId;
    private List<String> identities;
    private String owner;

    public RoomContentsClientResponse(String roomId, ArrayList<String> identities, String owner) {
        super(MessageType.ROOM_CONTENTS);
        this.roomId = roomId;
        this.identities = identities;
        this.owner = owner;
    }

}
