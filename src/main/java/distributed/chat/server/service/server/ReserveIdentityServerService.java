package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.ReserveIdentityServerRequest;
import distributed.chat.server.model.message.response.server.ReserveIdentityServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

import java.util.Objects;

public class ReserveIdentityServerService extends AbstractServerService<ReserveIdentityServerRequest, ReserveIdentityServerResponse>{
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
        if (Objects.equals(ServerState.localId, ServerState.leaderId)) {
            boolean reserved = !isUniqueIdentity(request.getIdentity());
            sendResponse(new ReserveIdentityServerResponse(request.getIdentity(), reserved), channel);
        } else {
            sendRequest(request, ServerState.serverChannels.get(ServerState.leaderId));
        }
    }

    private synchronized boolean isUniqueIdentity(String identity) {
        return !ServerState.globalClients.contains(identity);
    }
}
