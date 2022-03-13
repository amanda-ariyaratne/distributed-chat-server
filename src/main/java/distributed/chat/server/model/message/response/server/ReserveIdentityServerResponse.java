package distributed.chat.server.model.message.response.server;

import distributed.chat.server.model.message.MessageType;

public class ReserveIdentityServerResponse extends AbstractServerResponse{

    private final String identity;
    private final boolean approved;

    public ReserveIdentityServerResponse(String identity, boolean approved){
        // Todo: add reserve identity message type
        super(MessageType.NEW_IDENTITY);
        this.approved = approved;
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public boolean isApproved() {
        return approved;
    }
}
