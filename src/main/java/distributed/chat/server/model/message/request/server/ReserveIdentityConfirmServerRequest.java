package distributed.chat.server.model.message.request.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class ReserveIdentityConfirmServerRequest extends AbstractServerRequest {

    private final String identity;
    private final boolean reserved;


    public ReserveIdentityConfirmServerRequest(boolean reserved, String identity) {
        super(MessageType.RESERVE_IDENTITY_CONFIRM);
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
                "\"type\":\"" + RequestConstants.RESERVE_IDENTITY_CONFIRM + '"' +
                ", \"identity\":\"" + identity + '"' +
                ", \"reserved\":" + reserved +
                '}';
    }
}
