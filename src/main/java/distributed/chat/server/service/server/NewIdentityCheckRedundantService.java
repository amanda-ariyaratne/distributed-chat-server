package distributed.chat.server.service.server;

import distributed.chat.server.model.message.AbstractServerRequest;
import io.netty.channel.Channel;

public class NewIdentityCheckRedundantService extends AbstractServerService{
    private static NewIdentityCheckRedundantService instance;

    private NewIdentityCheckRedundantService(){}

    public static synchronized NewIdentityCheckRedundantService getInstance(){
        if (instance == null){
            instance = new NewIdentityCheckRedundantService();
        }
        return instance;
    }

    @Override
    public void processRequest(AbstractServerRequest request, Channel channel) {
        // Todo: check
        this.sendRequest(request, channel);
    }
}
