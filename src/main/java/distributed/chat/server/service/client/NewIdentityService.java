package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.NewIdentityClientRequest;
import distributed.chat.server.model.message.request.server.NewIdentityCheckRedundantRequest;
import distributed.chat.server.model.message.response.NewIdentityClientResponse;
import distributed.chat.server.service.server.NewIdentityCheckRedundantService;
import distributed.chat.server.states.ServerState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NewIdentityService extends AbstractClientService<NewIdentityClientRequest, NewIdentityClientResponse> {

    private static NewIdentityService instance;
    private Map<String, Client> pendingNewIdentityRequests = new ConcurrentHashMap<>();

    private NewIdentityService(){}

    public static synchronized NewIdentityService  getInstance(){
        if (instance == null){
            instance = new NewIdentityService();
        }
        return instance;
    }

    @Override
    public NewIdentityClientResponse processRequest(NewIdentityClientRequest request) {
        String identity = request.getIdentity();
        boolean approved;
        synchronized (this){
            approved = approveIdentity(identity, request);
            if (approved) {
                ServerState.clients.put(identity, request.getSender());
                /**
                 * Todo: if approved =>
                 * add to list
                 * Set Identity of client
                 * Set room to Main hall
                 */

            } else {
                if (pendingNewIdentityRequests.containsKey(identity)){
                    // Todo: Wait until response
                } else {
                    // Todo: send failed response
                }
            }
        }

        return new NewIdentityClientResponse(approved);
    }

    private boolean approveIdentity(String identity, NewIdentityClientRequest request) {
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
        boolean locallyRedundant = ServerState.clients.containsKey(identity);
        if (locallyRedundant)
            return true;
        else {
            NewIdentityCheckRedundantService.getInstance().processRequest(
                    new NewIdentityCheckRedundantRequest(identity),
                    ServerState.serverChannels.get(ServerState.leaderId)
            );

            pendingNewIdentityRequests.put(identity, request.getSender());

            return false;
        }
    }

}
