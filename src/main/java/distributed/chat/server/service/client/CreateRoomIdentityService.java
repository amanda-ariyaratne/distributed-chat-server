package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.CreateRoomClientRequest;
import distributed.chat.server.model.message.request.server.ReserveRoomServerRequest;
import distributed.chat.server.model.message.response.client.CreateRoomClientResponse;
import distributed.chat.server.service.server.ReserveRoomServerService;
import distributed.chat.server.states.ServerState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CreateRoomIdentityService extends AbstractClientService<CreateRoomClientRequest, CreateRoomClientResponse> {

    private static CreateRoomIdentityService instance;
    private Map<String, Client> pendingCreateRoomRequests = new ConcurrentHashMap<>();

    public static synchronized CreateRoomIdentityService getInstance() {
        if (instance == null) {
            instance = new CreateRoomIdentityService();
        }
        return instance;
    }

    /***
     * Called from CreateRoomHandler
     *
     * @param request {"type" : "createroom", "roomid" : "jokes"}
     */
    @Override
    public void processRequest(CreateRoomClientRequest request) {
        Client client = request.getSender();
        String roomId = request.getRoomId();

        boolean room_approved = false;
        synchronized (this) {
            if (!request.getSender().isAlready_room_owner()) { // check if client already a room owner
                room_approved = approveIdentity(roomId, request);
            }
            if (!room_approved && !ServerState.reservedRooms.containsKey(roomId)) {
                sendResponse(new CreateRoomClientResponse(roomId, false), client);
            }

            // create room
//            Room room = new Room(roomId, request.getSender());

            // add room object to rooms and activeRooms hashmaps
//            ServerState.globalRooms.add(roomId);
//            ServerState.localRooms.put(roomId, room);

            // send add room request to leader to notify that room has been created

            // create addRoomRequest object
//            AddRoomServerRequest addRoomServerRequest = new AddRoomServerRequest(
//                    ServerState.serverConfig.getServer_id(),
//                    roomId
//            );
            // get service
//            AddRoomServerService addRoomServerService = AddRoomServerService.getInstance();
            // process request
//            addRoomServerService.processRequest(
//                    addRoomServerRequest,
//                    ServerState.serverChannels.get(ServerState.serverConfig.getServer_id()));

            // change room main-hall to new room


            // create response object for client


        }

        // return : {"type" : "createroom", "roomid" : "jokes", "approved" : "true"}
//        return new CreateRoomClientResponse(roomId, room_approved);


    }

    private boolean approveIdentity(String roomId, CreateRoomClientRequest request) {
        if (isValidValue(roomId)) {
            return checkUniqueIdentity(roomId, request);
        }
        return false;
    }


    private boolean isValidValue(String identity) {
        if (identity.length() < 3 || identity.length() > 16) return false;
        else return Character.isAlphabetic(identity.charAt(0));
    }

    /***
     * Check Unique Identity
     * Return true -> send request to leader
     * Return false -> already room-id exists
     *
     * @param roomId String
     * @param request {"type" : "createroom", "roomid" : "jokes"}
     * @return boolean
     */
    private boolean checkUniqueIdentity(String roomId, CreateRoomClientRequest request) {
        boolean globallyRedundant = ServerState.globalRooms.contains(roomId);
        if (globallyRedundant) { // room-id already exists in server's global room list
            return false;
        } else { // check room-id with leader's list -> send request to leader
            // request message object - {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}
            ReserveRoomServerRequest reserveRoomServerRequest = new ReserveRoomServerRequest(ServerState.serverConfig.getServer_id(), roomId);
            // process request and get response
            ReserveRoomServerService.getInstance().processRequest(
                    reserveRoomServerRequest,
                    ServerState.serverChannels.get(ServerState.leaderId)
            );
            return true;
        }
    }

    /***
     *
     * @param reserved boolean
     * @param roomId String
     */
    public void approveIdentityProcessed(boolean reserved, String roomId) {
        Client client = ServerState.reservedRooms.get(roomId);
        ServerState.reservedRooms.remove(roomId);

        if (reserved) {
            // TODO check
            Room room = new Room(roomId, client);
            ServerState.localRooms.put(roomId, room);

//            AddRoomServerService.getInstance().broadcast(new AddRoomServerRequest(roomId)); // TODO
        }

        sendResponse(new CreateRoomClientResponse(roomId, reserved), client);

    }
}
