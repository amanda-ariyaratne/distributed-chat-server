package distributed.chat.server.model.message.request.client;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

public class WhoClientRequest extends AbstractClientRequest {

    public WhoClientRequest() {
        super(MessageType.WHO);
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + RequestConstants.WHO + '\'' +
                "}";
    }
}
