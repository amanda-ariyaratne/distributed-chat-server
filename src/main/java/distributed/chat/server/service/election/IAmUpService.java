package distributed.chat.server.service.election;

import distributed.chat.server.bootstrap.server.ServerAsClient;
import distributed.chat.server.model.ServerConfig;
import distributed.chat.server.model.message.election.IAmUpMessage;
import distributed.chat.server.model.message.election.ViewMessage;
import distributed.chat.server.states.ElectionStatus;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

import java.util.ArrayList;

public class IAmUpService extends FastBullyService<IAmUpMessage> {
    private static IAmUpService instance;

    private IAmUpService(){}

    public static synchronized IAmUpService getInstance(){
        if (instance == null){
            instance = new IAmUpService();
        }
        return instance;
    }

    public void broadcastIAmUpMessage() {
        broadcast(new IAmUpMessage(ServerState.localId));

        synchronized (ServerState.electionLock) {
            ServerState.viewMessagesReceived = new ArrayList<>();
            ServerState.electionStatus = ElectionStatus.WAITING_FOR_VIEW;
        }

        new Thread(() -> {
            try {
                Thread.sleep(ServerState.viewTimeout);
                ViewService viewService = ViewService.getInstance();
                viewService.handleViewMessageReception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public void processMessage(IAmUpMessage message, Channel channel) {
        if (!ServerState.serverChannels.containsKey(message.getServerId())) {
            ServerConfig configs = ServerState.servers.get(message.getServerId());
            new Thread(() -> {
                try {
                    ServerAsClient serverAsClient = new ServerAsClient(
                            configs.getServer_address(),
                            configs.getCoordination_port()
                    );
                    serverAsClient.start();
                    ServerState.serverChannels.put(configs.getServer_id(), serverAsClient.getChannel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        ViewMessage vm = new ViewMessage(ServerState.localId, ServerState.leaderId);
        channel.writeAndFlush(vm.toString());

    }
}
