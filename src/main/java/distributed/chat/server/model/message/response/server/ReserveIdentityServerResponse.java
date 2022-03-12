package distributed.chat.server.model.message.response.server;

import distributed.chat.server.model.message.MessageType;

public class ReserveIdentityServerResponse extends AbstractServerResponse{

    private boolean approved;

    public ReserveIdentityServerResponse(boolean approved){
        // Todo: add reserve identity message type
        super(MessageType.NEW_IDENTITY);
        this.approved = approved;
    }
}
