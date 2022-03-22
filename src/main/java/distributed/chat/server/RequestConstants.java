package distributed.chat.server;

public final class RequestConstants {

    public static final String NEW_IDENTITY                         = "newidentity";

    public static final String RESERVE_IDENTITY                     = "reserveidentity";
    public static final String RESERVE_IDENTITY_RESPONSE            = "reserveidentityresponse";

    public static final String RESERVE_IDENTITY_CONFIRM             = "reserveidentityconfirm";
    public static final String RESERVE_IDENTITY_CONFIRM_RESPONSE    = "reserveidentityconfirmresponse";

    public static final String ADD_IDENTITY                         = "addidentity";
    public static final String ADD_IDENTITY_RESPONSE                = "addidentityresponse";

    public static final String LIST                                 = "list";
    public static final String WHO                                  = "who";
    public static final String CREATE_ROOM                          = "createroom";
    public static final String JOIN_ROOM                            = "joinroom";
    public static final String MOVE_JOIN                            = "movejoin";
    public static final String DELETE_ROOM                          = "deleteroom";
    public static final String MESSAGE                              = "message";
    public static final String QUIT                                 = "quit";

    public static final String ADD_ROOM                             = "addroom";
    public static final String ADD_ROOM_RESPONSE                    = "addroomresponse";
    public static final String DELETE_IDENTITY                      = "deleteidentity";
    public static final String RESERVE_ROOM_CONFIRM                 = "reserveroomconfirm";
    public static final String RESERVE_ROOM                         = "reserveroomid";
    public static final String RESERVE_ROOM_RESPONSE                = "reserveroomresponse";
    public static final String RESERVE_ROOM_CONFIRM_RESPONSE        = "reserveroomconfirmresponse";
    public static final String ROOM_CHANGE                          = "roomchange";
    public static final String ROOM_CONTENTS                        = "roomcontents";
    public static final String ROOM_LIST                            = "roomlist";
    public static final String ROUTE                                = "route";
    public static final String SERVER_CHANGE                        = "serverchange";

    public static final String HEARTBEAT                            = "heartbeat";

    public static final String I_AM_UP                              = "iamup";
    public static final String ANSWER                               = "answer";
    public static final String COORDINATOR                          = "coordinator";
    public static final String ELECTION                             = "election";
    public static final String NOMINATION                           = "nomination";
    public static final String VIEW                                 = "view";

    public static final String SYNC_GLOBAL_LISTS                    = "syncgloballists";


    private RequestConstants() {
    }
}
