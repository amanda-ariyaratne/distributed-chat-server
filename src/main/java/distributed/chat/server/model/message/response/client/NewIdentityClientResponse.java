package distributed.chat.server.model.message.response.client;

import distributed.chat.server.model.message.MessageType;

public class NewIdentityClientResponse extends AbstractClientResponse {

    private boolean approved;

    public NewIdentityClientResponse(boolean approved) {
        super(MessageType.NEW_IDENTITY);
        this.approved = approved;
    }

}
