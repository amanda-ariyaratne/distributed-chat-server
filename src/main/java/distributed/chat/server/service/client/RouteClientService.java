package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.JoinRoomClientRequest;
import distributed.chat.server.model.message.response.client.RouteClientResponse;

public class RouteClientService extends AbstractClientService<JoinRoomClientRequest, RouteClientResponse> {

    private static RouteClientService instance;

    private RouteClientService() {
    }

    public static synchronized RouteClientService getInstance() {
        if (instance == null) {
            instance = new RouteClientService();
        }
        return instance;
    }

    @Override
    public void processRequest(JoinRoomClientRequest request) {

    }

    public void handleJoinRoomResponse(RouteClientResponse response, Client client){
        sendResponse(response, client);
    }
}
