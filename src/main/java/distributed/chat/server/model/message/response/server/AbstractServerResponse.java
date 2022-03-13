package distributed.chat.server.model.message.response.server;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.MessageType;

public abstract class AbstractServerResponse extends AbstractMessage {

    public AbstractServerResponse(MessageType type) {
        super(type);
    }

    public static class ReserveIdentityServerResponse extends distributed.chat.server.model.message.AbstractServerResponse {

        private final String identity;

        public ReserveIdentityServerResponse(String serverId, String identity, boolean reserved) {
            super(MessageType.RESERVE_IDENTITY);
            this.identity = identity;
        }

        public String getIdentity() {
            return identity;
        }
    }
}