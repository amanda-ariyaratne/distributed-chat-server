package distributed.chat.server.model.message.request.client;


import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.MessageType;

public class QuitClientRequest extends AbstractClientRequest {

    private Client sender;

    public QuitClientRequest() {
        //{"type" : "quit"}
        super(MessageType.QUIT);
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + RequestConstants.QUIT + '"' +
                "}";
    }
}
