package distributed.chat.server.service.server;

import distributed.chat.server.model.message.request.server.QuitServerRequest;
import distributed.chat.server.model.message.response.server.AbstractServerResponse;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

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

    @Override
    public void processRequest(QuitServerRequest request, Channel channel) {
        System.out.println("QuitServerService : process request");
        ServerState.globalRooms.remove(request.getIdentity());
    }

    public void broadcastRequest(QuitServerRequest request){
        broadcast(request);
    }
}
