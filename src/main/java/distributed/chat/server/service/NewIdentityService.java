package distributed.chat.server.service;

import distributed.chat.server.model.message.request.NewIdentityClientRequest;
import distributed.chat.server.model.message.response.NewIdentityClientResponse;

public class NewIdentityService extends AbstractClientService<NewIdentityClientRequest, NewIdentityClientResponse> {

    // TODO : Convert to Singleton

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
