package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.client.NewIdentityClientRequest;
import distributed.chat.server.model.message.request.server.AddIdentityServerRequest;
import distributed.chat.server.model.message.request.server.ReserveIdentityServerRequest;
import distributed.chat.server.model.message.response.client.NewIdentityClientResponse;
import distributed.chat.server.model.message.response.client.RoomChangeClientResponse;
import distributed.chat.server.service.server.AddIdentityServerService;
import distributed.chat.server.service.server.ReserveIdentityServerService;
import distributed.chat.server.states.ServerState;

import java.util.Objects;

public class NewIdentityService extends AbstractClientService<NewIdentityClientRequest, NewIdentityClientResponse> {

    private static NewIdentityService instance;

    private NewIdentityService() {
    }

    public static synchronized NewIdentityService getInstance() {
        if (instance == null) {
            instance = new NewIdentityService();
        }
        return instance;
    }

    @Override
    public void processRequest(NewIdentityClientRequest request) {
        String identity = request.getIdentity();
        Client client = request.getSender();
        boolean approved = false;
        synchronized (this) {
            if (isValidValue(identity)) {
                approved = !checkReservedIdentity(identity, request);
                if (approved) { // if leader
                    ServerState.reservedClients.put(identity, request.getSender());
                    approveIdentityProcessed(false, identity);
                } else if (!ServerState.reservedClients.containsKey(identity) && (Objects.equals(ServerState.localId, ServerState.leaderId))) {
                    sendResponse(new NewIdentityClientResponse(false), client);
                }

            } else {
                sendResponse(new NewIdentityClientResponse(false), client);
            }
        }
    }

    public synchronized void approveIdentityProcessed(boolean reserved, String identity) {
        Client client = ServerState.reservedClients.get(identity);
        ServerState.reservedClients.remove(identity);

        sendResponse(new NewIdentityClientResponse(!reserved), client);

        if (!reserved) {
            ServerState.localClients.put(identity, client);
            ServerState.globalClients.add(identity);
            client.setIdentity(identity);

            client.setRoom(ServerState.localRooms.get("MainHall-" + ServerState.localId));
            ServerState.localRooms.get("MainHall-" + ServerState.localId).addMember(client);
            AddIdentityServerService.getInstance().broadcast(new AddIdentityServerRequest(identity));

            JoinRoomClientService.getInstance().broadCastRoomChangeMessage(new RoomChangeClientResponse(
                    identity,
                    "",
                    "MainHall-" + ServerState.localId
            ), ServerState.localRooms.get("MainHall-" + ServerState.localId));
        }


    }

    private boolean isValidValue(String identity) {
        if (identity.length() < 3 || identity.length() > 16) return false;
        else return Character.isAlphabetic(identity.charAt(0));
    }

    private boolean checkReservedIdentity(String identity, NewIdentityClientRequest request) {
        boolean globallyRedundant = ServerState.globalClients.contains(identity);
        boolean alreadyReservedIdentity = ServerState.reservedClients.containsKey(identity);

        if (globallyRedundant || alreadyReservedIdentity) {
            sendResponse(new NewIdentityClientResponse(false), request.getSender());
            return true;
        } else {

            if (ServerState.localId != ServerState.leaderId) {
                ServerState.reservedClients.put(identity, request.getSender());
                ReserveIdentityServerService.getInstance().processRequest(
                        new ReserveIdentityServerRequest(identity),
                        ServerState.serverChannels.get(ServerState.leaderId)
                );
                return true;
            }
            return false;
        }
    }
}
