package distributed.chat.server.model.message;

public enum MessageType {
    NEW_IDENTITY,
    RESERVE_IDENTITY,
    RESERVE_IDENTITY_RESPONSE,
    RESERVE_IDENTITY_CONFIRM,
    RESERVE_IDENTITY_CONFIRM_RESPONSE,
    ADD_IDENTITY,
    ADD_IDENTITY_RESPONSE,

    LIST,
    WHO,

    ROOM_CHANGE, // Todo
    ROOM_LIST, // Todo
    ROOM_CONTENTS, // Todo

    CREATE_ROOM,
    JOIN_ROOM,
    ROUTE, // Todo
    MOVE_JOIN,
    SERVER_CHANGE, // Todo
    DELETE_ROOM,
    MESSAGE,
    QUIT,

    HEARTBEAT, // Todo

    RESERVE_ROOM, // Todo
    ADD_ROOM, // Todo
    DELETE_IDENTITY, // Todo
}
