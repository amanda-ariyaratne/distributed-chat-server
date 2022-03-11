package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class QuitClientRequest extends AbstractClientRequest {

    public QuitClientRequest() {
        super(MessageType.QUIT);
    }

}
