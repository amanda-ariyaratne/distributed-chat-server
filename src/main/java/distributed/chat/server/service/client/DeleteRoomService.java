package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.DeleteRoomClientRequest;
import distributed.chat.server.model.message.request.server.DeleteRoomServerRequest;
import distributed.chat.server.model.message.response.client.DeleteRoomClientResponse;
import distributed.chat.server.model.message.response.client.RoomChangeClientResponse;
import distributed.chat.server.service.server.DeleteRoomServerService;
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
        System.out.println("DeleteRoomService : process request");
        synchronized (ServerState.globalRoomListLock) {
            Client client = request.getSender();
            String roomId = request.getRoomId();

            if (isCapable(client, roomId)) { // client is the owner
                System.out.println("DeleteRoomService : process request : client is the owner");
                handleDelete(roomId, client, "MainHall-" + ServerState.localId);

            } else { // failed to delete
                System.out.println("DeleteRoomService : process request : failed to delete");
                // {"type" : "deleteroom", "roomid" : "jokes", "approved" : "false"}
                DeleteRoomClientResponse deleteRoomClientResponse = new DeleteRoomClientResponse(roomId, false);
                sendResponse(deleteRoomClientResponse, client);
            }
        }
    }

    public void handleDelete(String roomId, Client client, String mainhall) {


        System.out.println("DeleteRoomService : send response");
        // response
        DeleteRoomClientResponse deleteRoomClientResponse = new DeleteRoomClientResponse(roomId, true);
        sendResponse(deleteRoomClientResponse, client);

        // move client and other members to main-hall -> broadcast joinroom
        moveMembersToMainHall(roomId, mainhall);

        System.out.println("DeleteRoomService : handle delete");
        // inform other servers - {"type" : "deleteroom", "serverid" : "s1", "roomid" : "jokes"}
        DeleteRoomServerRequest deleteRoomServerRequest = new DeleteRoomServerRequest(ServerState.localId, roomId);
        DeleteRoomServerService.getInstance().broadcastRequest(deleteRoomServerRequest);

        // delete room
        ServerState.localRooms.remove(roomId);

    }

    private boolean isCapable(Client client, String roomId) {
        System.out.println("DeleteRoomService : isCapable");
        return (roomId.equals(client.getRoom().getRoomId()) // client's current room
                && (client.getRoom()).getOwner().getIdentity().equals(client.getIdentity())); // client is the owner
    }

    private void moveMembersToMainHall(String roomId, String mainhall) {
        System.out.println("DeleteRoomService : moveMembersToMainHall");
        Room room = ServerState.localRooms.get(roomId);
        Room mainHall = ServerState.localRooms.get("MainHall-" + ServerState.localId);

        for (Client member : room.getMembers()) {

            RoomChangeClientResponse roomChangeClientResponse;
            if (room.getOwner() == member) { // if owner
                roomChangeClientResponse = new RoomChangeClientResponse(member.getIdentity(), roomId, mainhall);
            }
            else {
                roomChangeClientResponse = new RoomChangeClientResponse(member.getIdentity(), roomId, "MainHall-" + ServerState.localId);
            }

            // broadcast to main-hall
            JoinRoomClientService.getInstance().broadCastRoomChangeMessage(roomChangeClientResponse, ServerState.localRooms.get("MainHall-" + ServerState.localId));
            // broadcast to current room
            JoinRoomClientService.getInstance().broadCastRoomChangeMessage(roomChangeClientResponse, ServerState.localRooms.get(roomId));
        }

        for (Client member : room.getMembers()) {
            // set main room as room of the member
            member.setRoom(mainHall);
            // remove from room
            room.removeMember(member);
            // add to main hall
            mainHall.addMember(member);
        }
    }
}
