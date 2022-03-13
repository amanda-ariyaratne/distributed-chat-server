package distributed.chat.server.model.message.response.server;

import distributed.chat.server.model.message.MessageType;

public class AddIdentityServerResponse extends AbstractServerResponse{

    private final boolean added;

    public AddIdentityServerResponse(boolean added){
        // Todo: add reserve identity message type
        super(MessageType.NEW_IDENTITY);
        this.added = added;
    }

    public boolean isAdded() {
        return added;
    }
}
