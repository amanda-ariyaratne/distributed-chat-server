package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.AddRoomServerRequest;
import distributed.chat.server.model.message.response.server.AddRoomServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class AddRoomServerService extends AbstractServerService<AddRoomServerRequest, AddRoomServerResponse> {

    private static AddRoomServerService instance;

    public static synchronized AddRoomServerService getInstance() {
        if (instance == null) {
            instance = new AddRoomServerService();
        }
        return instance;
    }

    /***
     * Leader's Side AddRoomServerRequest Handling
     *
     * @param request {"type" : "addroom", "serverid" : "s1", "roomid" : "jokes"}
     * @param channel Channel
     */
    public void handleRequest(AddRoomServerRequest request, Channel channel){
        // add to leader's room list
        ServerState.globalRooms.put(request.getRoomId(), request.getServerId());
//        System.out.println("\nGlobal room list ");
//        for (String id : ServerState.globalRooms.keySet()) {
//            System.out.println(id);
//        }
    }

    @Override
    public void processRequest(AddRoomServerRequest request, Channel channel) {

    }
}
