package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.ReserveIdentityServerRequest;
import distributed.chat.server.model.message.response.server.ReserveIdentityServerResponse;
import distributed.chat.server.service.client.NewIdentityService;
import io.netty.channel.Channel;

public class ReserveIdentityServerService extends AbstractServerService<ReserveIdentityServerRequest>{
    private static ReserveIdentityServerService instance;

    private ReserveIdentityServerService(){}

    public static synchronized ReserveIdentityServerService getInstance(){
        if (instance == null){
            instance = new ReserveIdentityServerService();
        }
        return instance;
    }

    @Override
    public void processRequest(ReserveIdentityServerRequest request, Channel channel) {
        // Todo: check
        sendRequest(request, channel);
    }

    public void approveIdentityRequestProcessed(ReserveIdentityServerResponse response){
        NewIdentityService.getInstance().approveIdentityProcessed(response.isApproved(), response.getIdentity());
    }
}
