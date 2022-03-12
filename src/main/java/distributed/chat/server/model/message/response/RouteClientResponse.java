package distributed.chat.server.model.message.response;

import distributed.chat.server.model.message.AbstractClientResponse;
import distributed.chat.server.model.message.MessageType;

public class RouteClientResponse extends AbstractClientResponse {

    private String roomId;
    private String host;
    private int port;

    public RouteClientResponse(String roomId, String host, int port) {
        super(MessageType.ROUTE);
        this.roomId = roomId;
        this.host = host;
        this.port = port;
    }

}
