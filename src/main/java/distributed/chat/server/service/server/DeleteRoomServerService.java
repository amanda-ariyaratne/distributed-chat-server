package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.DeleteRoomServerRequest;
import distributed.chat.server.model.message.response.server.AbstractServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class DeleteRoomServerService extends AbstractServerService<DeleteRoomServerRequest, AbstractServerResponse> {
    private static DeleteRoomServerService instance;

    private DeleteRoomServerService() {
    }

    public static synchronized DeleteRoomServerService getInstance() {
        if (instance == null) {
            instance = new DeleteRoomServerService();
        }
        return instance;
    }

    @Override
    public void processRequest(DeleteRoomServerRequest request, Channel channel) {
        synchronized (ServerState.globalRoomListLock){
            System.out.println("DeleteRoomServerService : Delete room from global list");
            ServerState.globalRooms.remove(request.getRoomId());
        }
    }

    public void broadcastRequest(DeleteRoomServerRequest request) {
        System.out.println("DeleteRoomServerService : Broadcast delete room to other servers");
        broadcast(request);
    }
}
