package distributed.chat.server.model.message.request.client;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class MoveJoinClientRequest extends AbstractClientRequest {

    private final String former;
    private final String roomid;
    private final String identity;
    private Client sender;

    public MoveJoinClientRequest(String former, String roomid, String identity) {
        super(MessageType.MOVE_JOIN);
        this.former = former;
        this.roomid = roomid;
        this.identity = identity;
    }

    public String getFormer() {
        return former;
    }

    public String getRoomid() {
        return roomid;
    }

    public String getIdentity() {
        return identity;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "{" +
                " \"type\" : \"" + RequestConstants.MOVE_JOIN + '\"' +
                ", \"former\" : \"" + former + '\"' +
                ", \"roomid\" : \"" + roomid + '\"' +
                ", \"identity\" : \"" + identity + '\"' +
                '}';
    }
}
