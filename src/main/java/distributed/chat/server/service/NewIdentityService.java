package distributed.chat.server.service;

import distributed.chat.server.bootstrap.server.ServerToClient;
import distributed.chat.server.model.message.request.NewIdentityClientRequest;
import distributed.chat.server.model.message.response.NewIdentityClientResponse;

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
    public NewIdentityClientResponse processRequest(NewIdentityClientRequest request) {
        String identity = request.getIdentity();
        boolean approved;
        synchronized (this){
            approved = approveIdentity(identity);
            if (approved) {
                ServerToClient.clients.put(identity, request.getSender());
            } else {

            }
            /**
             * Todo: if approved =>
             * add to list
             * Set Identity of client
             * Set room to Main hall
              */
        }

        return new NewIdentityClientResponse(approved);
    }

    private boolean approveIdentity(String identity) {
        if (isValidValue(identity)) {
            return !checkUniqueIdentity(identity);
        }
        return false;
    }

    private boolean isValidValue(String identity) {
        if (identity.length() < 3 || identity.length() > 16) return false;
        else return Character.isAlphabetic(identity.charAt(0));
    }

    private boolean checkUniqueIdentity(String identity) {
        boolean locallyRedundant = ServerToClient.clients.containsKey(identity);

        if (locallyRedundant)
            return true;

        /**
         * else {
         *      Todo: Ask from Leader
         * }
         */

        return true;
    }

}
