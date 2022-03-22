package distributed.chat.server.model.message.response.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class AddRoomServerResponse extends AbstractServerResponse{

    private final boolean added;

    public AddRoomServerResponse(boolean added) {
        // {"type" : "addroomresponse", "added" : "jokes"}
        super(MessageType.ADD_ROOM_RESPONSE);
        this.added = added;
    }

    public boolean isAdded() {
        return added;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.ADD_ROOM_RESPONSE + '"' +
                ", \"added\" : \"" + added + '"' +
                '}';
    }
}
