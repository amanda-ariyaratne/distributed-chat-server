package distributed.chat.server.service.server;

import distributed.chat.server.model.message.response.client.AbstractClientResponse;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.response.client.NewIdentityClientResponse;
import io.netty.channel.Channel;

public class ReserveIdentityServerService extends AbstractServerService{
    private static ReserveIdentityServerService instance;

    private ReserveIdentityServerService(){}

    public static synchronized ReserveIdentityServerService getInstance(){
        if (instance == null){
            instance = new ReserveIdentityServerService();
        }
        return instance;
    }

    @Override
    public AbstractClientResponse processRequest(AbstractServerRequest request, Channel channel) {
        // Todo: check
        sendRequest(request, channel);
        return new NewIdentityClientResponse(true);
    }
}
