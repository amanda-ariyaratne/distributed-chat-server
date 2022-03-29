package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.CreateRoomClientRequest;
import distributed.chat.server.model.message.request.server.AddRoomServerRequest;
import distributed.chat.server.model.message.request.server.ReserveRoomServerRequest;
import distributed.chat.server.model.message.response.client.CreateRoomClientResponse;
import distributed.chat.server.model.message.response.client.RoomChangeClientResponse;
import distributed.chat.server.service.server.AddRoomServerService;
import distributed.chat.server.service.server.ReserveRoomServerService;
import distributed.chat.server.states.ServerState;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/***
 * Service Class for handling client request to create new rooms
 */
public class CreateRoomService extends AbstractClientService<CreateRoomClientRequest, CreateRoomClientResponse> {

    private static CreateRoomService instance;
    private Set<String> pendingIdentityRequests = new HashSet<>();

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
                if (isValidValue(roomId)) { // if valid
                    room_approved = checkReservedRoomId(roomId, request);

                    if (room_approved) { // if leader
                        ServerState.reservedRooms.put(roomId, request.getSender());
                        approveRoomIdProcessed(false, roomId);
                    }
                    else if (!ServerState.reservedRooms.containsKey(roomId) && (Objects.equals(ServerState.localId, ServerState.leaderId))) { // if leader
                        sendResponse(new CreateRoomClientResponse(roomId, false), client);
                    }

                } else {
                    sendResponse(new CreateRoomClientResponse(roomId, false), client);
                }
            } else { // if already an owner
                sendResponse(new CreateRoomClientResponse(roomId, false), client);
            }
        }
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
    private boolean checkReservedRoomId(String roomId, CreateRoomClientRequest request) {
        boolean globallyRedundant = ServerState.globalRooms.containsKey(roomId);
        boolean alreadyReservedRoomId = ServerState.reservedRooms.containsKey(roomId);

        if (globallyRedundant || alreadyReservedRoomId) { // room-id already exists in server's global room list or reserved
            sendResponse(new CreateRoomClientResponse(roomId, false), request.getSender());
            return false;
        } else { // check room-id with leader's list -> send request to leader

            if (!Objects.equals(ServerState.localId, ServerState.leaderId)) { // if not leader
                ServerState.reservedRooms.put(roomId, request.getSender());

                // request message object - {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}
                ReserveRoomServerRequest reserveRoomServerRequest = new ReserveRoomServerRequest(ServerState.serverConfig.getServer_id(), roomId);
                // process request and get response
                ReserveRoomServerService.getInstance().processRequest(
                        reserveRoomServerRequest,
                        ServerState.serverChannels.get(ServerState.leaderId)
                );
                return false;
            }
            return true; // if leader return approved
        }
    }

    /***
     * Called from Reserved Room Confirm Server Service
     *
     * @param reserved boolean
     * @param roomId String
     */
    public void approveRoomIdProcessed(boolean reserved, String roomId) {
        Client client = ServerState.reservedRooms.get(roomId);
        ServerState.reservedRooms.remove(roomId);

        // send response to client
        // {"type" : "createroom", "roomid" : "jokes", "approved" : "true"}
        CreateRoomClientResponse createRoomClientResponse = new CreateRoomClientResponse(roomId, !reserved);
        sendResponse(createRoomClientResponse, client);

        if (!reserved) {
            // set client as the owner
            client.setAlready_room_owner(true);

            // create new room with new roomId and add to localRooms hashmap and global rooms
            Room room = new Room(roomId, client);
            ServerState.localRooms.put(roomId, room);
            ServerState.globalRooms.put(roomId, ServerState.localId);

            // broadcast to other servers
            AddRoomServerService addRoomServerService = AddRoomServerService.getInstance();
            // {"type" : "addroom", "serverid" : "s1", "roomid" : "jokes"}
            AddRoomServerRequest addRoomServerRequest = new AddRoomServerRequest(roomId, ServerState.localId);
            addRoomServerService.broadcast(addRoomServerRequest);

            // remove client from main hall add to new room
            client.setRoom(ServerState.localRooms.get(roomId));
            ServerState.localRooms.get("MainHall-" + ServerState.localId).removeMember(client);
            ServerState.localRooms.get(roomId).addMember(client);

            // broadcast
            RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(
                    client.getIdentity(),
                    "MainHall-" + ServerState.localId,
                    roomId);
            // broadcast room change to new room members
            JoinRoomClientService.getInstance().broadCastRoomChangeMessage(roomChangeClientResponse, room);

            // broadcast room change to mainhall members
            JoinRoomClientService.getInstance().broadCastRoomChangeMessage(roomChangeClientResponse, ServerState.localRooms.get("MainHall-" + ServerState.localId));
        }

    }
}
