package distributed.chat.server.model.message.response.client;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class NewIdentityClientResponse extends AbstractClientResponse {

    private final boolean approved;

    public NewIdentityClientResponse(boolean approved) {
        super(MessageType.NEW_IDENTITY);
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + RequestConstants.NEW_IDENTITY + '\'' +
                ", approved:" + approved +
                '}';
    }
}
