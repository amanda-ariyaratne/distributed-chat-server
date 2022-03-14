package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;

public class QuitClientRequest extends AbstractClientRequest {

    public QuitClientRequest() {
        super(MessageType.QUIT);
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.QUIT + '\'' +
                "}";
    }
}
