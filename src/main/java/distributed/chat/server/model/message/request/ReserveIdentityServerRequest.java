package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.AbstractServerRequest;
import distributed.chat.server.model.message.MessageType;

public class ReserveIdentityServerRequest extends AbstractServerRequest {

    private String serverId;
    private String identity;

    public ReserveIdentityServerRequest(String serverId, String identity) {
        super(MessageType.RESERVE_IDENTITY);
        this.serverId = serverId;
        this.identity = identity;
    }

}
