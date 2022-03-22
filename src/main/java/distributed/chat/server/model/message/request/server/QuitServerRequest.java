package distributed.chat.server.model.message.request.server;

import distributed.chat.server.model.message.MessageType;

public class QuitServerRequest extends AbstractServerRequest {

    private final String identity;

    public QuitServerRequest(String identity) {
        super(MessageType.QUIT);
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + MessageType.QUIT + '"' +
                "}";
    }
}
