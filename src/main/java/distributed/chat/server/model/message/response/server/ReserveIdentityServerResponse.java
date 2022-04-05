package distributed.chat.server.model.message.response.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class ReserveIdentityServerResponse extends AbstractServerResponse{

    private final String identity;
    private final boolean reserved;

    public ReserveIdentityServerResponse(String identity, boolean reserved){
        super(MessageType.RESERVE_IDENTITY_RESPONSE);
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
                "\"type\" : \"" + RequestConstants.RESERVE_IDENTITY_RESPONSE + '"' +
                ", \"identity\" : \"" + identity + '"' +
                ", \"reserved\" : \"" + reserved + '"' +
                '}';
    }
}
