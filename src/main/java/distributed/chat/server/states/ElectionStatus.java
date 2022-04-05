package distributed.chat.server.states;

public enum ElectionStatus {
    WAITING_FOR_VIEW,
    LEADER_ELECTED,
    ELECTION_STARTED,
    WAITING_FOR_COORDINATOR
}
