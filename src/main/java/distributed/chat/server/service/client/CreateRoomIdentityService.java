package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.AddRoomServerRequest;
import distributed.chat.server.model.message.request.ReserveRoomServerRequest;
import distributed.chat.server.model.message.request.client.CreateRoomClientRequest;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.response.ReserveRoomServerResponse;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;
import distributed.chat.server.model.message.response.client.CreateRoomClientResponse;
import distributed.chat.server.service.server.AddRoomServerService;
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

    @Override
    public CreateRoomClientResponse processRequest(CreateRoomClientRequest request) {
        // request : {"type" : "createroom", "roomid" : "jokes"}
        String roomId = request.getRoomId();
        boolean room_approved = false;
        synchronized (this) {
            if (!request.getSender().isAlready_room_owner()) { // check if client already a room owner
                // check if room id already exists in local room list
                room_approved = approveIdentity(roomId, request);
                // get response from leader

                // create room
                Room room = new Room(roomId, request.getSender());

                // add room object to rooms and activeRooms hashmaps
                ServerState.rooms.add(roomId);
                ServerState.activeRooms.put(roomId, room);

                // send add room request to leader to notify that room has been created

                // create addRoomRequest object
                AddRoomServerRequest addRoomServerRequest = new AddRoomServerRequest(
                        ServerState.serverConfig.getServer_id(),
                        roomId
                );
                // get service
                AddRoomServerService addRoomServerService = AddRoomServerService.getInstance();
                // process request
                addRoomServerService.processRequest(
                        addRoomServerRequest,
                        ServerState.serverChannels.get(ServerState.serverConfig.getServer_id()));

                // change room main-hall to new room


                // create response object for client

            } else {
                if (pendingCreateRoomRequests.containsKey(roomId)) {
                    // Todo: Wait until response
                } else {
                    // Todo: send failed response
                }
            }

        }

        // return : {"type" : "createroom", "roomid" : "jokes", "approved" : "true"}
        return new CreateRoomClientResponse(roomId, room_approved);


    }

    private boolean approveIdentity(String roomId, CreateRoomClientRequest request) {
        if (isValidValue(roomId)) {
            return !checkUniqueIdentity(roomId, request);
        }
        return false;
    }


    private boolean isValidValue(String identity) {
        if (identity.length() < 3 || identity.length() > 16) return false;
        else return Character.isAlphabetic(identity.charAt(0));
    }

    private boolean checkUniqueIdentity(String roomId, CreateRoomClientRequest request) {
        // request  = {"type" : "createroom", "roomid" : "jokes"}

        boolean locallyRedundant = ServerState.rooms.contains(roomId);
        if (locallyRedundant) { // room-id already exists in local room list
            return false;
        } else {
            // check room-id with leader's list

            // request message object - {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}
            ReserveRoomServerRequest reserveRoomServerRequest = new ReserveRoomServerRequest(ServerState.serverConfig.getServer_id(), roomId);
            // process request and get response
            ReserveRoomServerService.getInstance().processRequest(
                    reserveRoomServerRequest,
                    ServerState.serverChannels.get(ServerState.leaderId)
            );

            pendingCreateRoomRequests.put(roomId, request.getSender());

            // return message response
            return true;
        }
    }

}
