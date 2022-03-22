package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.DeleteRoomClientRequest;
import distributed.chat.server.model.message.request.client.NewIdentityClientRequest;
import distributed.chat.server.model.message.request.client.QuitClientRequest;
import distributed.chat.server.model.message.request.server.QuitServerRequest;
import distributed.chat.server.model.message.response.client.NewIdentityClientResponse;
import distributed.chat.server.model.message.response.client.RoomChangeClientResponse;
import distributed.chat.server.service.server.QuitServerService;
import distributed.chat.server.states.ServerState;

public class QuitService extends AbstractClientService<QuitClientRequest, RoomChangeClientResponse> {

    private static QuitService instance;

    private QuitService() {
    }

    public static synchronized QuitService getInstance() {
        if (instance == null) {
            instance = new QuitService();
        }
        return instance;
    }

    /***
     *
     * @param request {"type":"quit"}
     */
    @Override
    public void processRequest(QuitClientRequest request) {
        Client client = request.getSender();
        Room room = client.getRoom();

        if (client.isAlready_room_owner()) { // already a room owner
            // delete room
            DeleteRoomClientRequest deleteRoomClientRequest = new DeleteRoomClientRequest(room.getRoomId());
            deleteRoomClientRequest.setSender(client);
            DeleteRoomService.getInstance().handleDelete(room.getRoomId(), client, "");
        }

        // remove from client list
        ServerState.localClients.remove(client.getIdentity());
        ServerState.globalClients.remove(client.getIdentity());

        // server closes the connection
        ServerState.activeClients.remove(client.getCtx().channel().id());

        // broadcast to other servers
        QuitServerRequest quitServerRequest = new QuitServerRequest(client.getIdentity());
        QuitServerService.getInstance().broadcastRequest(quitServerRequest);

    }
}
