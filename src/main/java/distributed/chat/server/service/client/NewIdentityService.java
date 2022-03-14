package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.NewIdentityClientRequest;
import distributed.chat.server.model.message.request.server.AddIdentityServerRequest;
import distributed.chat.server.model.message.request.server.ReserveIdentityServerRequest;
import distributed.chat.server.model.message.response.client.NewIdentityClientResponse;
import distributed.chat.server.service.server.AddIdentityServerService;
import distributed.chat.server.service.server.ReserveIdentityServerService;
import distributed.chat.server.states.ServerState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NewIdentityService extends AbstractClientService<NewIdentityClientRequest, NewIdentityClientResponse> {

    private static NewIdentityService instance;

    private NewIdentityService(){}

    public static synchronized NewIdentityService  getInstance(){
        if (instance == null){
            instance = new NewIdentityService();
        }
        return instance;
    }

    @Override
    public void processRequest(NewIdentityClientRequest request) {
        String identity = request.getIdentity();
        Client client = request.getSender();
        boolean approved;
        synchronized (this){
            ServerState.reservedClients.put(identity, client);
            approved = isUniqueIdentity(identity, request);
        }

        if (!approved && !ServerState.reservedClients.containsKey(identity)){
            sendResponse(new NewIdentityClientResponse(false), client);
        }
    }

    public synchronized void approveIdentityProcessed(boolean isApproved, String identity){
        Client client = ServerState.reservedClients.get(identity);
        ServerState.reservedClients.remove(identity);

        if (isApproved) {
            ServerState.localClients.put(identity, client);
            client.setIdentity(identity);
            client.setRoom(ServerState.rooms.get("MainHall-" + ServerState.localId));
            AddIdentityServerService.getInstance().broadcast(new AddIdentityServerRequest(identity));
        }

        sendResponse(new NewIdentityClientResponse(isApproved), client);
    }

    private boolean isUniqueIdentity(String identity, NewIdentityClientRequest request) {
        if (isValidValue(identity)) {
            return !checkUniqueIdentity(identity, request);
        }
        return false;
    }

    private boolean isValidValue(String identity) {
        if (identity.length() < 3 || identity.length() > 16) return false;
        else return Character.isAlphabetic(identity.charAt(0));
    }

    private boolean checkUniqueIdentity(String identity, NewIdentityClientRequest request) {
        boolean locallyRedundant = ServerState.globalClients.contains(identity);
        boolean isReservedIdentity = ServerState.reservedClients.containsKey(identity);

        if (locallyRedundant || isReservedIdentity){
            return true;
        }
        else {
            ReserveIdentityServerService.getInstance().processRequest(
                    new ReserveIdentityServerRequest(identity),
                    ServerState.serverChannels.get(ServerState.leaderId)
            );

            return false;
        }
    }
}
