package distributed.chat.server.model.message.response.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class AddIdentityServerResponse extends AbstractServerResponse{

    private final boolean added;

    public AddIdentityServerResponse(boolean added){
        super(MessageType.ADD_IDENTITY_RESPONSE);
        this.added = added;
    }

    public boolean isAdded() {
        return added;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.ADD_IDENTITY_RESPONSE + '"' +
                ", \"added\" : \"" + added + '"' +
                '}';
    }
}
