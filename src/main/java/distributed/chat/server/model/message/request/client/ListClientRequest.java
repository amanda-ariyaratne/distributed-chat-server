package distributed.chat.server.model.message.request.client;

import distributed.chat.server.model.message.MessageType;

public class ListClientRequest extends AbstractClientRequest {

    public ListClientRequest() {
        super(MessageType.LIST);
    }

    @Override
    public String toString() {
        return "{" +
                "type:'" + MessageType.LIST + '\'' +
                "}";
    }
}
