package distributed.chat.server.model.message;

public abstract class AbstractClientRequest extends AbstractMessage {

    public AbstractClientRequest(MessageType type) {
        super(type);
    }
}
