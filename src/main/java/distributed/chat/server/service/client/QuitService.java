package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.DeleteRoomClientRequest;
import distributed.chat.server.model.message.request.client.QuitClientRequest;
import distributed.chat.server.model.message.request.server.QuitServerRequest;
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


        QuitServerRequest quitServerRequest;


        if (client.isAlready_room_owner()) { // already a room owner
            // delete room
            DeleteRoomClientRequest deleteRoomClientRequest = new DeleteRoomClientRequest(room.getRoomId());
            deleteRoomClientRequest.setSender(client);
            DeleteRoomService.getInstance().handleDelete(room.getRoomId(), client, "");

            quitServerRequest = new QuitServerRequest(client.getIdentity(), room.getRoomId());
        } else { // not an owner
            // room change response to client
            room.removeMember(client);
            RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(client.getIdentity(), room.getRoomId(), "");
            sendResponse(roomChangeClientResponse, client);

            quitServerRequest = new QuitServerRequest(client.getIdentity(), "");

        }

        // broadcast to other servers
        QuitServerService.getInstance().broadcastRequest(quitServerRequest);

        // remove from client list
        ServerState.localClients.remove(client.getIdentity());
        ServerState.globalClients.remove(client.getIdentity());

        // server closes the connection
        ServerState.activeClients.remove(client.getCtx().channel().id());
    }
}
