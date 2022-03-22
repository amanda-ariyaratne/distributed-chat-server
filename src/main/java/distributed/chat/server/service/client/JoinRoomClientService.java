package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.ServerConfig;
import distributed.chat.server.model.message.request.client.JoinRoomClientRequest;
import distributed.chat.server.model.message.response.client.RoomChangeClientResponse;
import distributed.chat.server.model.message.response.client.RouteClientResponse;
import distributed.chat.server.states.ServerState;

public class JoinRoomClientService extends AbstractClientService<JoinRoomClientRequest, RoomChangeClientResponse> {
    private static JoinRoomClientService instance;

    private JoinRoomClientService() {
    }

    public static synchronized JoinRoomClientService getInstance() {
        if (instance == null) {
            instance = new JoinRoomClientService();
        }
        return instance;
    }

    /***
     * client join other rooms if he/she is not the owner of the current chat room
     *
     * @param request {"type" : "joinroom", "roomid" : "jokes"}
     */
    @Override
    public void processRequest(JoinRoomClientRequest request) {
        synchronized (this) {
            Client client = request.getSender();
            String former_roomId = client.getRoom().getRoomId();
            String new_roomId = request.getRoomId();

            // check if the client is a room owner
            if (!client.isAlready_room_owner()) {
                if (ServerState.localRooms.containsKey(new_roomId)) { // room exists in local rooms
                    // change room id of the client
                    client.setRoom(ServerState.localRooms.get(new_roomId));
                    // remove from former room
                    ServerState.localRooms.get(former_roomId).removeMember(client);
                    // add client to the room
                    ServerState.localRooms.get(new_roomId).addMember(client);

                    // broadcast a roomchange message
                    RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(client.getIdentity(), former_roomId, new_roomId);
                    //  - to the members of the former chat room
                    broadCastRoomChangeMessage(roomChangeClientResponse, ServerState.localRooms.get(former_roomId));
                    //  - to the members of the new chat room and to the client joining the room
                    broadCastRoomChangeMessage(roomChangeClientResponse, ServerState.localRooms.get(new_roomId));

                } else if (ServerState.globalRooms.containsKey(new_roomId)) { // room is in a another server

                    // get other server info
                    String other_server_id = ServerState.globalRooms.get(new_roomId);
                    ServerConfig serverConfig = ServerState.servers.get(other_server_id);
                    String host = serverConfig.getServer_address();
                    int port = serverConfig.getClients_port();

                    // first replies to the client with a route message redirecting it to another server.
                    // {"type" : "route", "roomid" : "jokes", "host" : "122.134.2.4", "port" : "4445"}
                    RouteClientResponse routeClientResponse = new RouteClientResponse(new_roomId, host, port);
                    RouteClientService.getInstance().handleJoinRoomResponse(routeClientResponse, client);

                    // remove from former room
                    ServerState.localRooms.get(former_roomId).removeMember(client);
                    // server removes the client from its list
                    ServerState.localClients.remove(client.getIdentity());

                    // broadcasts a roomchange message to all the members of the former chat room
                    RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(client.getIdentity(), former_roomId, new_roomId);
                    broadCastRoomChangeMessage(roomChangeClientResponse, ServerState.localRooms.get(former_roomId));


                } else { // room does not exist -> join is not successful
                    // {"type" : "roomchange", "identity" : "Maria", "former" : "jokes", "roomid" : "jokes"}
                    RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(client.getIdentity(), former_roomId, former_roomId);
                    sendResponse(roomChangeClientResponse, client);
                }
            } else { // this client is a room owner
                RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(client.getIdentity(), former_roomId, former_roomId);
                sendResponse(roomChangeClientResponse, client);
            }

        }

    }

    public void broadCastRoomChangeMessage(RoomChangeClientResponse response, Room room) {
        broadcast(response, room);
    }
}
