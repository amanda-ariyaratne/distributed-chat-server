package distributed.chat.server.model.message.request.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class QuitServerRequest extends AbstractServerRequest {

    private final String identity;

    public QuitServerRequest(String identity) {
        //{"type" : "quit", "identity" : "name"}
        super(MessageType.QUIT_SERVER);
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : \"" + RequestConstants.QUIT_SERVER + '"' +
                ", \"identity\" : \"" + identity + '"' +
                "}";
    }
}
