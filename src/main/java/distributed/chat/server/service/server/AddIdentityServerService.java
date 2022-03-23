package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.AddIdentityServerRequest;
import distributed.chat.server.model.message.response.server.AddIdentityServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class AddIdentityServerService extends AbstractServerService<AddIdentityServerRequest, AddIdentityServerResponse>{
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
        System.out.println("AddIdentityServerService : process request");
        synchronized (this) {
            ServerState.globalClients.add(request.getIdentity());
        }
    }
}
