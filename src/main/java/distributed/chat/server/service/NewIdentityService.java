package distributed.chat.server.service;

import distributed.chat.server.model.message.AbstractClientRequest;
import distributed.chat.server.model.message.request.NewIdentityClientRequest;

public class NewIdentityService extends AbstractClientService {

    private static NewIdentityService instance;

    private NewIdentityService(){}

    public static synchronized NewIdentityService  getInstance(){
        if (instance == null){
            instance = new NewIdentityService();
        }
        return instance;
    }

    @Override
    public void processRequest(AbstractClientRequest request) {
        //
    }

    @Override
    public void handleRequest(AbstractClientRequest request) {
        //
    }
}
