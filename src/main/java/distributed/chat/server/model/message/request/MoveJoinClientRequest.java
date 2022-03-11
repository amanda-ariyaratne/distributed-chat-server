package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class MoveJoinClientRequest extends AbstractClientRequest {

    private String former;
    private String roomid;
    private String identity;

    public MoveJoinClientRequest(String former, String roomid, String identity) {
        super(MessageType.MOVE_JOIN);
        this.former = former;
        this.roomid = roomid;
        this.identity = identity;
    }
}
