package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.SyncGlobalListsServerRequest;
import distributed.chat.server.model.message.response.server.AbstractServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class SyncGlobalListsServerService extends AbstractServerService<SyncGlobalListsServerRequest, AbstractServerResponse> {
    private static SyncGlobalListsServerService instance;

    private SyncGlobalListsServerService() {
    }

    public static synchronized SyncGlobalListsServerService getInstance() {
        if (instance == null) {
            instance = new SyncGlobalListsServerService();
        }
        return instance;
    }

    @Override
    public void processRequest(SyncGlobalListsServerRequest request, Channel channel) {
        if (Objects.equals(ServerState.localId, ServerState.leaderId)) {
            synchronized (this){
//                System.out.println("\nSync : Add Clients");
                ServerState.globalClients.addAll(Arrays.asList(request.getClients()));

//                System.out.println("\nSync : Add rooms");
                for (Map.Entry<String, String> room : request.getRooms().entrySet()) {
                    if (!ServerState.globalRooms.containsKey(room.getKey())) {
                        ServerState.globalRooms.put(room.getKey(), room.getValue());
                    }
                }
            };
        }
    }
}
