package distributed.chat.server.service.election;

import distributed.chat.server.model.message.election.AnswerMessage;
import distributed.chat.server.model.message.election.CoordinatorMessage;
import distributed.chat.server.states.ElectionStatus;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Map;

public class CoordinatorService extends FastBullyService<CoordinatorMessage> {
    private static CoordinatorService instance;

    private CoordinatorService(){}

    public static synchronized CoordinatorService getInstance(){
        if (instance == null){
            instance = new CoordinatorService();
        }
        return instance;
    }

    public void sendCoordinatorMessage() {
        // System.out.println("INFO: Sending coordinator message for lower servers");
        for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
            if (server.getKey().compareTo(ServerState.localId) < 0) {
                CoordinatorMessage coordinatorMessage = new CoordinatorMessage(ServerState.localId);
                ServerState.serverChannels.get(server.getKey()).writeAndFlush(coordinatorMessage.toString());
            }
        }
        ServerState.leaderId = ServerState.localId;
        ServerState.electionStatus = ElectionStatus.LEADER_ELECTED;
    }

    @Override
    public void processMessage(CoordinatorMessage message, Channel channel) {
        synchronized (ServerState.electionLock) {
            ServerState.coordinatorMessage = message;
            ServerState.leaderId = message.getServerId();
            ServerState.electionStatus = ElectionStatus.LEADER_ELECTED;
            System.out.println("INFO: New leader set to " + ServerState.leaderId);
        }
    }

    public void handleCoordinatorMessageReception(String highestServerId) {
        boolean restartNomination = false;
        synchronized (ServerState.electionLock) {
            if (ServerState.electionStatus == ElectionStatus.WAITING_FOR_COORDINATOR) {
                if (ServerState.coordinatorMessage != null) {
                    ServerState.leaderId = ServerState.coordinatorMessage.getServerId();
                    ServerState.electionStatus = ElectionStatus.LEADER_ELECTED;

                    ServerState.coordinatorMessage = null;
                    ServerState.answerMessagesReceived = new ArrayList<>();
                } else {
                    // System.out.println("INFO: Coordinator message is not received");
                    for (int i = 0; i < ServerState.answerMessagesReceived.size(); i++) {
                        AnswerMessage am = ServerState.answerMessagesReceived.get(i);
                        if (am.getServerId().equals(highestServerId)) {
                            ServerState.answerMessagesReceived.remove(i);
                        }
                    }
                    restartNomination = true;
                }
            }
        }
        if (restartNomination) {
            AnswerService answerService = AnswerService.getInstance();
            answerService.handleAnswerMessageReception(true);
        }
    }
}
