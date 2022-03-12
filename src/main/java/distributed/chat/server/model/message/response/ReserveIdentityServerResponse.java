package distributed.chat.server.model.message.response;

import distributed.chat.server.model.message.AbstractServerResponse;
import distributed.chat.server.model.message.MessageType;

public class ReserveIdentityServerResponse extends AbstractServerResponse {

    private String serverId;
    private String identity;
    private boolean reserved;

    public ReserveIdentityServerResponse(String serverId, String identity, boolean reserved) {
        super(MessageType.RESERVE_IDENTITY);
        this.serverId = serverId;
        this.identity = identity;
        this.reserved = reserved;
    }

}
