package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.CreateRoomClientRequest;
import distributed.chat.server.model.message.request.server.AddRoomServerRequest;
import distributed.chat.server.model.message.request.server.ReserveRoomServerRequest;
import distributed.chat.server.model.message.response.client.CreateRoomClientResponse;
import distributed.chat.server.service.server.AddRoomServerService;
import distributed.chat.server.service.server.ReserveRoomServerService;
import distributed.chat.server.states.ServerState;

/***
 * Service Class for handling client request to create new rooms
 */
public class CreateRoomService extends AbstractClientService<CreateRoomClientRequest, CreateRoomClientResponse> {

    private static CreateRoomService instance;

    public static synchronized CreateRoomService getInstance() {
        if (instance == null) {
            instance = new CreateRoomService();
        }
        return instance;
    }

    /***
     * Called from CreateRoom Inbound Handler
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
        }
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
     * Called from Reserved Room Confirm Server Service
     *
     * @param approved boolean
     * @param roomId String
     */
    public void approveIdentityProcessed(boolean approved, String roomId) {
        Client client = ServerState.reservedRooms.get(roomId);
        ServerState.reservedRooms.remove(roomId);

        if (approved) {
            // TODO check
            // create new room with new roomId and add to localRooms hashmap
            Room room = new Room(roomId, client);
            ServerState.localRooms.put(roomId, room);

            // broadcast to other servers
            AddRoomServerService addRoomServerService = AddRoomServerService.getInstance();
            // {"type" : "addroom", "roomid" : "jokes"}
            AddRoomServerRequest addRoomServerRequest = new AddRoomServerRequest(roomId);
            addRoomServerService.broadcast(addRoomServerRequest);

            // remove client from main hall add to new room
            client.setRoom(ServerState.localRooms.get(roomId));
            //TODO: move the client from mainhall to new room
            // add the client to the room's members list
            room.addMember(client);

            AddRoomServerService.getInstance().broadcast(new AddRoomServerRequest(ServerState.localId,roomId)); // TODO:check
        }

        // send response to client
        // {"type" : "createroom", "roomid" : "jokes", "approved" : "true"}
        CreateRoomClientResponse createRoomClientResponse = new CreateRoomClientResponse(roomId, approved);
        sendResponse(createRoomClientResponse, client);

        // TODO : broadcast room change

    }
}
