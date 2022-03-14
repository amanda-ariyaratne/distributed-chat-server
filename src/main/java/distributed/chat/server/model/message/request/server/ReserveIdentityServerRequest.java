package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.MessageType;

public class ReserveIdentityServerRequest extends AbstractServerRequest {

    private final String identity;

    public ReserveIdentityServerRequest(String identity) {
        super(MessageType.RESERVE_IDENTITY);
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.RESERVE_IDENTITY + '\'' +
                ", identity:'" + identity + '\'' +
                '}';
    }
}
