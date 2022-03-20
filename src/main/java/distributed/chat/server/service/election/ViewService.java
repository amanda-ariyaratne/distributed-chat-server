package distributed.chat.server.service.election;

import distributed.chat.server.model.message.election.ViewMessage;
import distributed.chat.server.states.ElectionStatus;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class ViewService extends FastBullyService<ViewMessage> {
    private static ViewService instance;

    private ViewService(){}

    public static synchronized ViewService getInstance(){
        if (instance == null){
            instance = new ViewService();
        }
        return instance;
    }

    @Override
    public void processMessage(ViewMessage message, Channel channel) {
        synchronized (ServerState.electionLock) {
            ServerState.viewMessagesReceived.add(message);
        }
    }

    public void handleViewMessageReception() {
        if (ServerState.electionStatus == ElectionStatus.WAITING_FOR_VIEW) {
            synchronized (ServerState.electionLock) {
                if (ServerState.viewMessagesReceived.size() == 0) {
                    // no view messages received
                    // this server is the coordinator
                    ServerState.leaderId = ServerState.localId;
                } else {
                    // Set leader Id given by the view messages
                    boolean isHighest = true;
                    for (int i = 0; i < ServerState.viewMessagesReceived.size(); i++) {
                        ViewMessage vm = ServerState.viewMessagesReceived.get(i);
                        if (vm.getCurrentLeaderId().compareTo(ServerState.leaderId) > 0) {
                            ServerState.leaderId = vm.getCurrentLeaderId();
                        }
                        if (vm.getServerId().compareTo(ServerState.localId) > 0) {
                            isHighest = false;
                        }
                    }
                    if (isHighest) {
                        ServerState.leaderId = ServerState.localId;

                        CoordinatorService coordinatorService = CoordinatorService.getInstance();
                        coordinatorService.sendCoordinatorMessage();
                    }

                }
                ServerState.electionStatus = ElectionStatus.LEADER_ELECTED;
            }
        }
    }
}
