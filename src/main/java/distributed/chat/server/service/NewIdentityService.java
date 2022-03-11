package distributed.chat.server.service;

import distributed.chat.server.model.message.AbstractClientRequest;
import distributed.chat.server.model.message.request.NewIdentityClientRequest;

public class NewIdentityService extends AbstractClientService {

    // TODO : Convert to Singleton

    @Override
    public void processRequest(AbstractClientRequest request) {

        String identity = request.getIdentity();
        System.out.println("identity : " + identity);
        try {
            approved = approveIdentity(identity);
        } catch (InterruptedException | ConnectException e) {
            logger.error("error when sending new identity to leader " + e.getMessage());
            request.incrementTries();
            retried = ServerState.getInstance().addRetryRequest(request);
        }

        String approvalString;
        if (approved) approvalString = "true";
        else approvalString = "false";
        return ReplyObjects.newIdentityReply(approvalString);
    }

    @Override
    public void handleRequest(AbstractClientRequest request) {
        NewIdentityClientRequest req
            JSONObject reply = processRequest();
            if (!retried) {
                sendResponse(reply);
            }
            if (approved) {
                getClient().setIdentity(request.getIdentity());
                getClient().setRoom(ChatClientServer.getMainHal());
                // if is leader add to global client list
                if (ServerState.getInstance().isCoordinator()) {
                    GlobalClient gClient = new GlobalClient(request.getIdentity(),
                            ServerState.getInstance().getServerInfo().getServerId());
                    LeaderState.getInstance().checkAndAddClient(gClient);
                }
                JSONObject roomChange = ReplyObjects.newIdRoomChange(getClient().getIdentity(),
                        ChatClientServer.getMainHal().getRoomId());
                sendResponse(roomChange);
                broadcast(roomChange, ChatClientServer.getMainHal());
            }

    }
}
