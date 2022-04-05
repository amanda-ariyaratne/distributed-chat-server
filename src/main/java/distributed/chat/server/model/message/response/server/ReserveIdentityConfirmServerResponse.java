package distributed.chat.server.model.message.response.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class ReserveIdentityConfirmServerResponse extends AbstractServerResponse {

    private final String identity;
    private final boolean reserved;

    public ReserveIdentityConfirmServerResponse(String identity, boolean reserved) {
        super(MessageType.RESERVE_IDENTITY_CONFIRM_RESPONSE);
        this.reserved = reserved;
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public boolean isReserved() {
        return reserved;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.RESERVE_IDENTITY_CONFIRM_RESPONSE + '"' +
                ", \"identity\" : \"" + identity + '"' +
                ", \"reserved\" : \"" + reserved + '"' +
                '}';
    }
}
