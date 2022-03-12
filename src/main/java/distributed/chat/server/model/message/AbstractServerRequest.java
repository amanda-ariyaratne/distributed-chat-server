package distributed.chat.server.model.message;

public abstract class AbstractServerRequest extends AbstractMessage {
    public AbstractServerRequest(MessageType type) {
        super(type);
    }
}
