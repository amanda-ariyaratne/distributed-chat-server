package distributed.chat.server.model.message;

public abstract class AbstractMessage {

    private MessageType type;

    public AbstractMessage(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
