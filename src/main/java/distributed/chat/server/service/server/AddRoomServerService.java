package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.request.server.AddRoomServerRequest;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class AddRoomServerService extends AbstractServerService{

    private static AddRoomServerService instance;

    public static synchronized AddRoomServerService getInstance() {
        if (instance == null) {
            instance = new AddRoomServerService();
        }
        return instance;
    }

    @Override
    public void processRequest(AbstractServerRequest request, Channel channel) {
        // Todo: check response type -> AbstractServerResponse
        // send to leader
        sendRequest(request, channel);
        // get response from leader
    }

    /***
     * Leader's Side AddRoomServerRequest Handling
     *
     * @param request {"type" : "addroom", "serverid" : "s1", "roomid" : "jokes"}
     * @param channel Channel
     */
    public void handleRequest(AddRoomServerRequest request, Channel channel){
        // add to leader's room list
        ServerState.rooms.add(request.getRoomId());

        // broadcast to all the servers
    }
}
