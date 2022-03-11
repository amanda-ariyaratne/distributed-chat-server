package distributed.chat.server.service;

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
        boolean approved = false;
        approved = approveIdentity(identity);

        return new NewIdentityClientResponse(approved);
    }

    private boolean approveIdentity(String identity) {
        // TODO : add logic
        return true;
    }

}
