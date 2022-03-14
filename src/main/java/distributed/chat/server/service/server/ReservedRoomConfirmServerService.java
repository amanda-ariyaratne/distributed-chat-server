package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.ReserveRoomConfirmServerRequest;
import distributed.chat.server.model.message.response.server.ReserveRoomConfirmServerResponse;
import distributed.chat.server.service.client.CreateRoomIdentityService;
import distributed.chat.server.service.client.NewIdentityService;
import io.netty.channel.Channel;

public class ReservedRoomConfirmServerService  extends AbstractServerService<ReserveRoomConfirmServerRequest, ReserveRoomConfirmServerResponse> {

    private static ReservedRoomConfirmServerService instance;

    public static synchronized ReservedRoomConfirmServerService getInstance(){
        if (instance == null){
            instance = new ReservedRoomConfirmServerService();
        }
        return instance;
    }

    @Override
    public void processRequest(ReserveRoomConfirmServerRequest request, Channel channel) {
        // get CreateRoomIdentityService
        CreateRoomIdentityService createRoomIdentityService = CreateRoomIdentityService.getInstance();

        createRoomIdentityService.approveIdentityProcessed(request.isReserved(), request.getRoomId());
    }
}
