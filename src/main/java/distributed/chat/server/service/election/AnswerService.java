package distributed.chat.server.service.election;

import distributed.chat.server.model.message.election.AnswerMessage;
import distributed.chat.server.states.ElectionStatus;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class AnswerService extends FastBullyService<AnswerMessage> {
    private static AnswerService instance;

    private AnswerService(){}

    public static synchronized AnswerService getInstance(){
        if (instance == null){
            instance = new AnswerService();
        }
        return instance;
    }


    @Override
    public void processMessage(AnswerMessage message, Channel channel) {
        synchronized (ServerState.electionLock) {
            // System.out.println(ServerState.localId + " INFO: Add answer message to ServerState.answerMessagesReceived");
            ServerState.answerMessagesReceived.add(message);
        }
    }

    public void handleAnswerMessageReception(boolean reelect) {
        String highestServerId = null;
        boolean waitForCoordinator = false;
        boolean restartElection = false;
        if (ServerState.electionStatus == ElectionStatus.ELECTION_STARTED) {
            synchronized (ServerState.electionLock) {
                if (ServerState.answerMessagesReceived.isEmpty()) {
                    // System.out.println(ServerState.localId + " WARN: Answer messages received is empty");
                    if (reelect) {
                        restartElection = true;
                    } else {
                        // no answer messages received
                        // this server is the coordinator
                        ServerState.leaderId = ServerState.localId;
                        ServerState.electionStatus = ElectionStatus.LEADER_ELECTED;
                        System.out.println(ServerState.localId + " INFO: New leader is to " + ServerState.leaderId);
                        CoordinatorService coordinatorService = CoordinatorService.getInstance();
                        coordinatorService.sendCoordinatorMessage();
                    }

                } else {
                    // determines the highest server id of the answering processes
                    for (int i = 0; i < ServerState.answerMessagesReceived.size(); i++) {
                        AnswerMessage am = ServerState.answerMessagesReceived.get(i);
                        if (highestServerId == null || am.getServerId().compareTo(highestServerId) > 0) {
                            highestServerId = am.getServerId();
                        }
                    }
                    // System.out.println(ServerState.localId + " INFO: Nominate " + highestServerId);
                    // send nomination message
                    NominationService nominationService = NominationService.getInstance();
                    nominationService.sendNominationMessage(highestServerId);

                    ServerState.coordinatorMessage = null;
                    ServerState.electionStatus = ElectionStatus.WAITING_FOR_COORDINATOR;
                    waitForCoordinator = true;
                }

            }

            if (waitForCoordinator) {
                // wait for coordinator message
                String finalHighestServerId = highestServerId;
                new Thread(() -> {
                    try {
                        // System.out.println(ServerState.localId + " INFO: Waiting for coordinator message");
                        Thread.sleep(ServerState.coordinatorTimeout);
                        CoordinatorService coordinatorService = CoordinatorService.getInstance();
                        coordinatorService.handleCoordinatorMessageReception(finalHighestServerId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            if (restartElection) {
                // System.out.println(ServerState.localId + " INFO: Restarting election");
                ElectionService electionService = ElectionService.getInstance();
                electionService.startElection();
            }

        }
    }
}
