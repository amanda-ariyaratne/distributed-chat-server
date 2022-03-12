package distributed.chat.server.model.message;

public abstract class AbstractServerResponse extends AbstractMessage {

    public AbstractServerResponse(MessageType type) {
        super(type);
    }
}