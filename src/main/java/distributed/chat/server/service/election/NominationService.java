package distributed.chat.server.service.election;

import distributed.chat.server.model.message.election.NominationMessage;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;

public class NominationService extends FastBullyService<NominationMessage> {
    private static NominationService instance;

    private NominationService(){}

    public static synchronized NominationService getInstance(){
        if (instance == null){
            instance = new NominationService();
        }
        return instance;
    }

    @Override
    public void processMessage(NominationMessage message, Channel channel) {
        synchronized (ServerState.electionLock) {
            ServerState.nominationMessage = message;

            CoordinatorService coordinatorService = CoordinatorService.getInstance();
            coordinatorService.sendCoordinatorMessage();
        }
    }

    public void sendNominationMessage(String highestServerId) {
        ServerState.serverChannels.get(highestServerId).writeAndFlush(new NominationMessage(ServerState.localId).toString());
    }
}
