package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.ReserveIdentityServerRequest;
import distributed.chat.server.model.message.response.server.ReserveIdentityServerResponse;
import distributed.chat.server.service.client.NewIdentityService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

import java.util.Objects;
import java.util.SplittableRandom;

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
        System.out.println("ReserveIdentityServerService : process request");
        if (Objects.equals(ServerState.localId, ServerState.leaderId)) {
            System.out.println("if leader");
            boolean isUnique = isUniqueIdentity(request.getIdentity());
            System.out.println("is unique"+isUnique);
            sendResponse(new ReserveIdentityServerResponse(request.getIdentity(), isUnique), channel);

        } else {
            System.out.println("not leader");
            sendRequest(request, ServerState.serverChannels.get(ServerState.leaderId));
        }
    }

    private synchronized boolean isUniqueIdentity(String identity) {
        boolean isUnique = !ServerState.globalClients.contains(identity);
        if (isUnique)
            ServerState.globalClients.add(identity);
        return isUnique;
    }
}
