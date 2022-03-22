package distributed.chat.server.model.message.response.client;

import distributed.chat.server.model.message.MessageType;

public class MessageClientResponse extends AbstractClientResponse {

    private final String content;
    private final String identity;

    public MessageClientResponse(String content, String identity) {
        super(MessageType.MESSAGE_RESPONSE);
        this.content = content;
        this.identity = identity;
    }

    public String getContent() {
        return content;
    }

    public String getIdentity() {
        return identity;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + MessageType.MESSAGE_RESPONSE + '"' +
                ", \"identity\":\"" + identity + '"' +
                ", \"content\":\"" + content + '"' +
                '}';
    }
}
