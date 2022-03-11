package distributed.chat.server.model.message.request;

import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.AbstractClientRequest;

public class WhoClientRequest extends AbstractClientRequest {

    public WhoClientRequest() {
        super(MessageType.WHO);
    }

}
