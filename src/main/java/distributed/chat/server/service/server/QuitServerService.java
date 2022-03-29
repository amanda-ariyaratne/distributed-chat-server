package distributed.chat.server.service.server;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.message.request.server.QuitServerRequest;
import distributed.chat.server.model.message.response.server.AbstractServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

import java.util.Objects;

public class QuitServerService extends AbstractServerService<QuitServerRequest, AbstractServerResponse> {
    private static QuitServerService instance;

    private QuitServerService() {
    }

    public static synchronized QuitServerService getInstance() {
        if (instance == null) {
            instance = new QuitServerService();
        }
        return instance;
    }

    /***
     *
     * @param request {"type" : "quitserver", "identity" : "name", "roomid" : "room"}
     * @param channel channel
     */
    @Override
    public void processRequest(QuitServerRequest request, Channel channel) {
        System.out.println("QuitServerService : process request");
        synchronized (this) {
            ServerState.globalClients.remove(request.getIdentity());
            System.out.println(request.getIdentity() + " removed from global clients : ");
            for (String id : ServerState.globalClients) {
                System.out.println(id);
            }
            
            if (!Objects.equals(request.getRoomId(), "")){ // if owner
                System.out.println("Owner delete room from globalRooms "+ request.getRoomId());
                ServerState.globalRooms.remove(request.getRoomId());
                for (String rid : ServerState.globalRooms.keySet()) {
                    System.out.println(rid);
                }
            }

        }

    }

    public void broadcastRequest(QuitServerRequest request) {
        System.out.println("QuitServerService : broadcast to other servers");
        broadcast(request);
    }
}
