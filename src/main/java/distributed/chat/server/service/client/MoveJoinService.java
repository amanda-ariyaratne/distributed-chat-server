package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.MoveJoinClientRequest;
import distributed.chat.server.model.message.response.client.RoomChangeClientResponse;
import distributed.chat.server.model.message.response.client.ServerChangeClientResponse;
import distributed.chat.server.states.ServerState;

public class MoveJoinService extends AbstractClientService<MoveJoinClientRequest, ServerChangeClientResponse> {
    private static MoveJoinService instance;

    private MoveJoinService() {
    }

    public static synchronized MoveJoinService getInstance() {
        if (instance == null) {
            instance = new MoveJoinService();
        }
        return instance;
    }

    /***
     * Upon receiving the route message, the client initiates a movejoin request to the new server
     *
     * @param request {"type" : "movejoin", "former" : "MainHall-s1", "roomid" : "jokes", "identity" : "Maria"}
     */
    @Override
    public void processRequest(MoveJoinClientRequest request) {
        Client client = request.getSender();
        String roomId = request.getRoomid();
        String former_roomId = request.getFormer();
        if (ServerState.localRooms.containsKey(roomId)) { // if room exists
            // add to client list
            ServerState.localClients.put(client.getIdentity(), client);
            // add to room
            Room room = ServerState.localRooms.get(roomId);
            room.addMember(client);
            // broadcast roomchange msg to all the members
            RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(client.getIdentity(), former_roomId, roomId);
            JoinRoomClientService.getInstance().broadCastRoomChangeMessage(roomChangeClientResponse, room);
            // response to client  {"type" : "serverchange", "approved" : "true", "serverid" : "s2"}
            ServerChangeClientResponse serverChangeClientResponse = new ServerChangeClientResponse(true, ServerState.localId);
            sendResponse(serverChangeClientResponse, client);

            System.out.println(" room change client response: " + roomChangeClientResponse);
            System.out.println("server change client response : " + serverChangeClientResponse);

        } else { // room does not exist
            System.out.println("room does not exist");
            // the server places the client in its MainHall chat room
            Room mainhall = ServerState.localRooms.get("MainHall-" + ServerState.localId);
            mainhall.addMember(client);

            System.out.println("add to main hall "+ServerState.localId);
            // server broadcasts a roomchange message with the roomid as the MainHall-s2
            RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(client.getIdentity(), former_roomId, roomId);
            JoinRoomClientService.getInstance().broadCastRoomChangeMessage(roomChangeClientResponse, mainhall);
            System.out.println("response " + roomChangeClientResponse);
        }
    }

}
