package distributed.chat.server.model.message;

public enum MessageType {
    NEW_IDENTITY,
    ROOM_CHANGE,
    LIST,
    ROOM_LIST,
    WHO,
    ROOM_CONTENTS,
    CREATE_ROOM,
    JOIN_ROOM,
    ROUTE,
    MOVE_JOIN,
    SERVER_CHANGE,
    DELETE_ROOM,
    MESSAGE,
    QUIT,
    RESERVE_IDENTITY,
    RESERVE_IDENTITY_CONFIRM,
    ADD_IDENTITY,
    RESERVE_ROOM,
    ADD_ROOM,
    DELETE_IDENTITY,

    HEARTBEAT,

    I_AM_UP,
    VIEW,
    COORDINATOR,
    ELECTION,
    ANSWER,
    NOMINATION
}
