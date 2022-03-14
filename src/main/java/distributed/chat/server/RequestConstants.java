package distributed.chat.server;

public final class RequestConstants {

    public final static String NEW_IDENTITY = "newidentity";
    public final static String RESERVE_IDENTITY = "reserveidentity";
    public final static String LIST = "list";
    public final static String WHO = "who";
    public final static String CREATE_ROOM = "createroom";
    public final static String JOIN_ROOM = "joinroom";
    public final static String MOVE_JOIN = "movejoin";
    public final static String DELETE_ROOM = "deleteroom";
    public final static String MESSAGE = "message";
    public final static String QUIT = "quit";
    public static final String RESERVE_IDENTITY_CONFIRM = "reserveidentityconfirm";
    public static final String ADD_IDENTITY = "addidentity";

    private RequestConstants() {
    }
}
