package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.ReserveRoomConfirmServerRequest;
import distributed.chat.server.model.message.response.server.ReserveRoomConfirmServerResponse;
import distributed.chat.server.service.client.CreateRoomService;
import io.netty.channel.Channel;

public class ReservedRoomConfirmServerService  extends AbstractServerService<ReserveRoomConfirmServerRequest, ReserveRoomConfirmServerResponse> {

    private static ReservedRoomConfirmServerService instance;

    public static synchronized ReservedRoomConfirmServerService getInstance(){
        if (instance == null){
            instance = new ReservedRoomConfirmServerService();
        }
        return instance;
    }

    /***
     * Called from Reserve Room Confirm Inbound Handler
     *
     * @param request {"type" : "reserveroomconfirm", "roomid" : "jokes", "reserved" : "true"}
     * @param channel Channel
     */
    @Override
    public void processRequest(ReserveRoomConfirmServerRequest request, Channel channel) {
        // get Create Room Identity Service
        CreateRoomService createRoomService = CreateRoomService.getInstance();
        // approve identity of room
        createRoomService.approveRoomIdProcessed(request.isReserved(), request.getRoomId());
    }
}
