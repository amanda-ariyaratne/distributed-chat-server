package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.ReserveRoomServerRequest;
import distributed.chat.server.model.message.response.server.ReserveRoomServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

import java.util.Objects;

/***
 * Process ReserveRoomHandler requests
 */
public class ReserveRoomServerService extends AbstractServerService <ReserveRoomServerRequest, ReserveRoomServerResponse> {

    private static ReserveRoomServerService instance;

    public static synchronized ReserveRoomServerService getInstance() {
        if (instance == null) {
            instance = new ReserveRoomServerService();
        }
        return instance;
    }

    /***
     *
     * @param request {"type" : "reserveroomid", "serverid" : "s1", "roomid" : "jokes"}
     * @param channel Channel
     */
    @Override
    public void processRequest(ReserveRoomServerRequest request, Channel channel) {
        // if leader -> check the room validity
        if (Objects.equals(ServerState.localId, ServerState.leaderId)) {
            boolean reserved = !isUniqueIdentity(request.getRoomId(), request.getServerId());
            // send response to slave
            // {"type" : "reserveroomresponse", "roomid" : "jokes", "reserved" : "true"}
            ReserveRoomServerResponse reserveRoomServerResponse = new ReserveRoomServerResponse(request.getRoomId(), reserved);
            sendResponse(reserveRoomServerResponse, channel);
        }
        else { // if slave -> send to leader
            sendRequest(request, ServerState.serverChannels.get(ServerState.leaderId));
        }
    }

    private synchronized boolean isUniqueIdentity(String roomId, String serverId) {
        boolean isUnique = !ServerState.globalRooms.containsKey(roomId);
        if (isUnique)
            ServerState.globalRooms.put(roomId, serverId);
        return isUnique;
    }
}
