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
        System.out.println("QuitService : process request");
        Client client = request.getSender();
        Room room = client.getRoom();

        QuitServerRequest quitServerRequest;

        if (client.isAlready_room_owner()) { // already a room owner
            System.out.println("client already a owner");
            // delete room
            DeleteRoomClientRequest deleteRoomClientRequest = new DeleteRoomClientRequest(room.getRoomId());
            deleteRoomClientRequest.setSender(client);
            DeleteRoomService.getInstance().handleDelete(room.getRoomId(), client, "");

            quitServerRequest = new QuitServerRequest(client.getIdentity(), room.getRoomId());
        } else { // not a owner
            // room change response to client
            System.out.println("client is not an room owner");
            room.removeMember(client);
            System.out.println("Current room member no = " + room.getMembers().size());
            RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(client.getIdentity(), room.getRoomId(), "");
            sendResponse(roomChangeClientResponse, client);

            quitServerRequest = new QuitServerRequest(client.getIdentity(), "");

        }

        System.out.println("remove client from list");
        // remove from client list
        ServerState.localClients.remove(client.getIdentity());
        ServerState.globalClients.remove(client.getIdentity());

        // server closes the connection
        System.out.println("close connections");
        ServerState.activeClients.remove(client.getCtx().channel().id());


        System.out.println("broadcast to other servers");
        // broadcast to other servers
        QuitServerService.getInstance().broadcastRequest(quitServerRequest);

    }
}
