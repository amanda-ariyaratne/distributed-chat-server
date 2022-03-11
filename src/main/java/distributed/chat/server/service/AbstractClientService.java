package distributed.chat.server.service;

import distributed.chat.server.model.message.AbstractClientRequest;

public abstract class AbstractClientService {

    public abstract void processRequest(AbstractClientRequest request);
    public abstract void handleRequest(AbstractClientRequest request);

}
