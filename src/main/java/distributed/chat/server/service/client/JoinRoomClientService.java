package distributed.chat.server.service.client;

import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.JoinRoomClientRequest;
import distributed.chat.server.model.message.response.client.RoomChangeClientResponse;

public class JoinRoomClientService extends AbstractClientService<JoinRoomClientRequest, RoomChangeClientResponse> {
    private static JoinRoomClientService instance;

    private JoinRoomClientService(){}

    public static synchronized JoinRoomClientService  getInstance(){
        if (instance == null){
            instance = new JoinRoomClientService();
        }
        return instance;
    }

    @Override
    public void processRequest(JoinRoomClientRequest request) {}

    public void broadCastRoomChangeMessage(RoomChangeClientResponse response, Room room) {
        broadcast(response, room);
    }
}
