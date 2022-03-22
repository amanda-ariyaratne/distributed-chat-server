package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.WhoClientRequest;
import distributed.chat.server.model.message.response.client.RoomContentsClientResponse;
import distributed.chat.server.states.ServerState;

import java.util.ArrayList;

public class WhoService extends AbstractClientService<WhoClientRequest, RoomContentsClientResponse> {

    private static WhoService instance;

    private WhoService() {
    }

    public static synchronized WhoService getInstance() {
        if (instance == null) {
            instance = new WhoService();
        }
        return instance;
    }

    /***
     * client ask for the list of clients in the current chat room
     * server replies with the list of clients in the chat room
     *
     * @param request {"type" : "who"}
     */
    @Override
    public void processRequest(WhoClientRequest request) {
        System.out.println("Who Service : process request");
        // get client's current room
        Room room = request.getSender().getRoom();
        if (room != null){

            // get members in room
            ArrayList<String> members_ids = new ArrayList<>();
            System.out.println("members = "+members_ids);
            for (Client member : room.getMembers()){
                members_ids.add(member.getIdentity());
            }
            // response
            RoomContentsClientResponse roomContentsClientResponse = new RoomContentsClientResponse(room.getRoomId(),members_ids , room.getOwner().getIdentity());
            sendResponse(roomContentsClientResponse, request.getSender());

            System.out.println("response : "+ roomContentsClientResponse);
        }
    }
}
