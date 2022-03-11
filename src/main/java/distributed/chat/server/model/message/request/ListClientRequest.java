package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class ListClientRequest extends AbstractClientRequest {

    public ListClientRequest() {
        super(MessageType.LIST);
    }

}
