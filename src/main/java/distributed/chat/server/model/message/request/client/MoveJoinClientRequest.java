package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;

public class MoveJoinClientRequest extends AbstractClientRequest {

    private final String former;
    private final String roomid;
    private final String identity;

    public MoveJoinClientRequest(String former, String roomid, String identity) {
        super(MessageType.MOVE_JOIN);
        this.former = former;
        this.roomid = roomid;
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.MOVE_JOIN + '\'' +
                ", former:'" + former + '\'' +
                ", roomid:'" + roomid + '\'' +
                ", identity:'" + identity + '\'' +
                '}';
    }
}
