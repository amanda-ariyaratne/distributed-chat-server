package distributed.chat.server.model.message;

public enum MessageType {
    NEW_IDENTITY,
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

    RESERVE_IDENTITY,
    RESERVE_IDENTITY_CONFIRM,
    ADD_IDENTITY,

    RESERVE_ROOM, // Todo
    ADD_ROOM, // Todo
    DELETE_IDENTITY, // Todo
}
