package distributed.chat.server.model.message.response;


import distributed.chat.server.model.message.MessageType;
import distributed.chat.server.model.message.response.client.AbstractClientResponse;

public class DeleteRoomClientResponse extends AbstractClientResponse {

    private String roomId;
    private boolean approved;

    public DeleteRoomClientResponse(String roomId, boolean approved) {
        super(MessageType.DELETE_ROOM);
        this.roomId = roomId;
        this.approved = approved;
    }


}
