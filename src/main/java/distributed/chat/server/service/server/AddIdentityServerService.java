package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.AddIdentityServerRequest;
import io.netty.channel.Channel;

public class AddIdentityServerService extends AbstractServerService<AddIdentityServerRequest>{
    private static AddIdentityServerService instance;

    private AddIdentityServerService(){}

    public static synchronized AddIdentityServerService getInstance(){
        if (instance == null){
            instance = new AddIdentityServerService();
        }
        return instance;
    }

    @Override
    public void processRequest(AddIdentityServerRequest request, Channel channel) {
        // Todo: check
        sendRequest(request, channel);
        // return new AddIdentityServerResponse(true);
    }
}
