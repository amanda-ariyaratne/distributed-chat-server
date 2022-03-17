package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.DeleteRoomClientRequest;
import distributed.chat.server.model.message.response.client.DeleteRoomClientResponse;
import distributed.chat.server.model.message.response.client.RoomChangeClientResponse;
import distributed.chat.server.states.ServerState;

public class DeleteRoomService extends AbstractClientService<DeleteRoomClientRequest, DeleteRoomClientResponse> {
    private static DeleteRoomService instance;

    private DeleteRoomService() {
    }

    public static synchronized DeleteRoomService getInstance() {
        if (instance == null) {
            instance = new DeleteRoomService();
        }
        return instance;
    }

    /***
     * If the client is the owner of the chat room, he/she can delete the room
     *
     * @param request {"type" : "deleteroom", "roomid" : "jokes"}
     */
    @Override
    public void processRequest(DeleteRoomClientRequest request) {
        synchronized (this) {
            Client client = request.getSender();
            String roomId = request.getRoomId();

            if (isCapable(client, roomId)) { // client is the owner
                // move client and other members to main-hall -> broadcast joinroom
                moveMembersToMainHall(roomId);
                // TODO : inform other servers - {"type" : "deleteroom", "serverid" : "s1", "roomid" : "jokes"}

                // delete room
                ServerState.localRooms.remove(roomId);

                // response
                DeleteRoomClientResponse deleteRoomClientResponse = new DeleteRoomClientResponse(roomId, true);
                sendResponse(deleteRoomClientResponse, client);

            } else { // failed to delete
                // {"type" : "deleteroom", "roomid" : "jokes", "approved" : "false"}
                DeleteRoomClientResponse deleteRoomClientResponse = new DeleteRoomClientResponse(roomId, false);
                sendResponse(deleteRoomClientResponse, client);
            }
        }
    }

    private boolean isCapable(Client client, String roomId) {
        return (roomId.equals(client.getRoom().getRoomId()) // client's current room
                && (client.getRoom()).getOwner().getIdentity().equals(client.getIdentity())); // client is the owner
    }

    private void moveMembersToMainHall(String roomId) {
        Room room = ServerState.localRooms.get(roomId);
        Room mainHall = ServerState.localRooms.get("MainHall-" + ServerState.localId);

        for (Client member : room.getMembers()) {
            // set main room as room of the member
            member.setRoom(mainHall);
            // broadcast
            RoomChangeClientResponse roomChangeClientResponse = new RoomChangeClientResponse(member.getIdentity(), roomId, "MainHall-" + ServerState.localId);
            JoinRoomClientService.getInstance().broadCastRoomChangeMessage(roomChangeClientResponse, ServerState.localRooms.get(roomId));
        }
    }
}
