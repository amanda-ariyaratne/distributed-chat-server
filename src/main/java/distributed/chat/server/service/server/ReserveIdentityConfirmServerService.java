package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.ReserveIdentityConfirmServerRequest;
import distributed.chat.server.model.message.response.server.ReserveIdentityConfirmServerResponse;
import distributed.chat.server.service.client.NewIdentityService;
import io.netty.channel.Channel;

public class ReserveIdentityConfirmServerService extends AbstractServerService<ReserveIdentityConfirmServerRequest, ReserveIdentityConfirmServerResponse> {

    private static ReserveIdentityConfirmServerService instance;

    private ReserveIdentityConfirmServerService(){}

    public static synchronized ReserveIdentityConfirmServerService getInstance(){
        if (instance == null){
            instance = new ReserveIdentityConfirmServerService();
        }
        return instance;
    }

    @Override
    public void processRequest(ReserveIdentityConfirmServerRequest request, Channel channel) {
        NewIdentityService.getInstance().approveIdentityProcessed(request.isReserved(), request.getIdentity());
    }
}
