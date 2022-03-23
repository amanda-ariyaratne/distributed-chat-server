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

public class NewIdentityService extends AbstractClientService<NewIdentityClientRequest, NewIdentityClientResponse> {

    private static NewIdentityService instance;

    private NewIdentityService(){}

    public static synchronized NewIdentityService  getInstance(){
        if (instance == null){
            instance = new NewIdentityService();
        }
        return instance;
    }

    @Override
    public void processRequest(NewIdentityClientRequest request) {
        String identity = request.getIdentity();
        Client client = request.getSender();
        boolean approved;
        synchronized (this){
            approved = isUniqueIdentity(identity, request);
        }

        if (approved) {
            approveIdentityProcessed(true, identity);
        }
        else if (!ServerState.reservedClients.containsKey(identity)){
            System.out.println("send already taken or reserved response");
            sendResponse(new NewIdentityClientResponse(false), client);
        }
    }

    public synchronized void approveIdentityProcessed(boolean reserved, String identity){
        System.out.println("approve identity processed");
        Client client = ServerState.reservedClients.get(identity);
        ServerState.reservedClients.remove(identity);

        if (!reserved) {
            System.out.println("is approved ");
            System.out.println("identity " + identity);
            System.out.println("client " + client);
            ServerState.localClients.put(identity, client);
            ServerState.globalClients.add(identity);
            client.setIdentity(identity);

            System.out.println("Add " + identity + " to MainHall");
            client.setRoom(ServerState.localRooms.get("MainHall-" + ServerState.localId));
            ServerState.localRooms.get("MainHall-" + ServerState.localId).addMember(client);
            AddIdentityServerService.getInstance().broadcast(new AddIdentityServerRequest(identity));
        }
        System.out.println("send response");
        sendResponse(new NewIdentityClientResponse(!reserved), client);
        JoinRoomClientService.getInstance().broadCastRoomChangeMessage(new RoomChangeClientResponse(
                identity,
                "",
                "MainHall-" + ServerState.localId
        ), ServerState.localRooms.get("MainHall-" + ServerState.localId));
    }

    private boolean isUniqueIdentity(String identity, NewIdentityClientRequest request) {
        if (isValidValue(identity)) {
            return !checkUniqueIdentity(identity, request);
        }
        return false;
    }

    private boolean isValidValue(String identity) {
        if (identity.length() < 3 || identity.length() > 16) return false;
        else return Character.isAlphabetic(identity.charAt(0));
    }

    // Todo: change the fun name
    private boolean checkUniqueIdentity(String identity, NewIdentityClientRequest request) {
        System.out.println("check unique id");
        boolean globallyRedundant = ServerState.globalClients.contains(identity);
        boolean alreadyReservedIdentity = ServerState.reservedClients.containsKey(identity);

        if (globallyRedundant || alreadyReservedIdentity){
            System.out.println("globally redundant or reserved");
            return true;
        }
        else {

            if (ServerState.localId != ServerState.leaderId){
                System.out.println("send reserveIdentityServerRequest");
                ServerState.reservedClients.put(identity, request.getSender());
                ReserveIdentityServerService.getInstance().processRequest(
                        new ReserveIdentityServerRequest(identity),
                        ServerState.serverChannels.get(ServerState.leaderId)
                );

            }

            return false;
        }
    }
}
