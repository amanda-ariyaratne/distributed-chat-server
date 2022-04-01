package distributed.chat.server.service.election;

import distributed.chat.server.model.message.election.AnswerMessage;
import distributed.chat.server.model.message.election.ElectionMessage;
import distributed.chat.server.states.ElectionStatus;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Map;

public class ElectionService extends FastBullyService<ElectionMessage> {

    private static ElectionService instance;

    private ElectionService(){}

    public static synchronized ElectionService getInstance(){
        if (instance == null){
            instance = new ElectionService();
        }
        return instance;
    }

    public void startElection() {
        // System.out.println(ServerState.localId + " INFO: Starting election");
        synchronized (ServerState.electionLock) {
            ServerState.electionStatus = ElectionStatus.ELECTION_STARTED;
            ServerState.answerMessagesReceived = new ArrayList<>();
        }

        // System.out.println(ServerState.localId + " INFO: sending election messages to higher servers");
        ElectionMessage em = new ElectionMessage(ServerState.localId);
        for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
            if (server.getKey().compareTo(ServerState.localId) > 0) {
                ServerState.serverChannels.get(server.getKey()).writeAndFlush(em.toString());
            }
        }

        new Thread(() -> {
            try {
                // System.out.println(ServerState.localId + " INFO: Waiting for answer messages");
                Thread.sleep(ServerState.answerTimeout);
                AnswerService answerService = AnswerService.getInstance();
                answerService.handleAnswerMessageReception(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void processMessage(ElectionMessage message, Channel channel) {
        // send answer message
        channel.writeAndFlush(new AnswerMessage(ServerState.localId).toString());
        // System.out.println(ServerState.localId + " INFO: Sent Answer message");

        synchronized (ServerState.electionLock) {
            ServerState.coordinatorMessage = null;
            ServerState.nominationMessage = null;
        }

        new Thread(() -> {
            try {
                // System.out.println(ServerState.localId + " INFO: Waiting for Nomination message or Coordinator message");
                Thread.sleep(ServerState.nominatorOrCoordinatorTimeout);
                synchronized (ServerState.electionLock) {
                    if (ServerState.nominationMessage == null && ServerState.coordinatorMessage == null) {
                        // System.out.println(ServerState.localId + " INFO: No Nomination message or Coordinator message received");
                        ElectionService electionService = ElectionService.getInstance();
                        electionService.startElection();

                    } else {
                        ServerState.coordinatorMessage = null;
                        ServerState.nominationMessage = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
