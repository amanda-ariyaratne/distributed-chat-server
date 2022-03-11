package distributed.chat.server.service;

import distributed.chat.server.model.message.AbstractClientRequest;
import distributed.chat.server.model.message.AbstractClientResponse;

public abstract class AbstractClientService<S extends AbstractClientRequest, T extends AbstractClientResponse> {

    public abstract T processRequest(S request);

}
