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
        System.out.println("INFO: Broadcasting IAmUp Message to all servers");
        broadcast(new IAmUpMessage(ServerState.localId));

        synchronized (ServerState.electionLock) {
            ServerState.viewMessagesReceived = new ArrayList<>();
            ServerState.electionStatus = ElectionStatus.WAITING_FOR_VIEW;
        }

        new Thread(() -> {
            try {
                // System.out.println("INFO: Waiting for View messages");
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
            new Thread(() -> {
                try {
                    ServerConfig configs = ServerState.servers.get(message.getServerId());
                    ServerAsClient serverAsClient = new ServerAsClient(
                            configs.getServer_id(),
                            configs.getServer_address(),
                            configs.getCoordination_port()
                    );
                    serverAsClient.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        ViewMessage vm = new ViewMessage(ServerState.localId, ServerState.leaderId);
        channel.writeAndFlush(vm.toString());

//        if (ServerState.localId.equals(ServerState.leaderId)){
//            System.out.println("Sending Synced List");
//            SyncGlobalListsServerRequest syncLists = new SyncGlobalListsServerRequest(
//                    ServerState.globalClients.toArray(new String[ServerState.globalClients.size()]),
//                    ServerState.globalRooms
//            );
//            channel.writeAndFlush(syncLists.toString());
//        }
    }
}
