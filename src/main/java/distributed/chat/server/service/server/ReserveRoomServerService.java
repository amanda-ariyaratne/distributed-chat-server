package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.ReserveRoomServerRequest;
import distributed.chat.server.model.message.request.server.AbstractServerRequest;
import distributed.chat.server.model.message.response.ReserveRoomServerResponse;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class ReserveRoomServerService extends AbstractServerService {

    private static ReserveRoomServerService instance;

    public static synchronized ReserveRoomServerService getInstance() {
        if (instance == null) {
            instance = new ReserveRoomServerService();
        }
        return instance;
    }

    @Override
    public AbstractClientResponse processRequest(AbstractServerRequest request, Channel channel) {
        // Todo: check response type -> AbstractServerResponse
        // send to leader
        sendRequest(request, channel);
        // get response from leader

        return null;
    }

    // leader side handle request
    public void handleRequest(ReserveRoomServerRequest request, Channel channel) {
        // {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}

        // check if already room-id exists in roomlist
        boolean exists = ServerState.rooms.contains(request.getRoomId());

        ReserveRoomServerResponse reserveRoomServerResponse;
        if (!exists) {
            // add to reserved list
            ServerState.reservedRooms.add(request.getRoomId());
            // create response object
            // {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes", "approved" : true}
            reserveRoomServerResponse = new ReserveRoomServerResponse(
                    request.getServerId(),
                    request.getRoomId(),
                    true);
        } else {
            // create response object
            reserveRoomServerResponse = new ReserveRoomServerResponse(
                    request.getServerId(),
                    request.getRoomId(),
                    false);
        }
        // send response from leader to server
        // TODO : sendRespond(reserveRoomServerResponse, channel);
    }
}
