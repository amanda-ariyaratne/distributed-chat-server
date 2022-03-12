package distributed.chat.server;

import com.google.gson.*;
import distributed.chat.server.model.message.request.client.*;

import java.lang.reflect.Type;

public class ClientRequestDeserializer implements JsonDeserializer<AbstractClientRequest> {

    @Override
    public AbstractClientRequest deserialize(JsonElement jsonElement, Type type,
                                             JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        final JsonObject requestJson = jsonElement.getAsJsonObject();

        AbstractClientRequest request;

        switch (requestJson.get("type").getAsString()) {
            case RequestConstants.NEW_IDENTITY:
                request = new NewIdentityClientRequest(requestJson.get("identity").getAsString());
                break;
            case RequestConstants.LIST:
                request = new ListClientRequest();
                break;
            case RequestConstants.WHO:
                request = new WhoClientRequest();
                break;
            case RequestConstants.CREATE_ROOM:
                request = new CreateRoomClientRequest(requestJson.get("roomid").getAsString());
                break;
            case RequestConstants.JOIN_ROOM:
                request = new JoinRoomClientRequest(requestJson.get("roomid").getAsString());
                break;
            case RequestConstants.MOVE_JOIN:
                request = new MoveJoinClientRequest(
                        requestJson.get("former").getAsString(),
                        requestJson.get("identity").getAsString(),
                        requestJson.get("roomid").getAsString()
                );
                break;
            case RequestConstants.DELETE_ROOM:
                request = new DeleteRoomClientRequest(requestJson.get("roomid").getAsString());
                break;
            case RequestConstants.MESSAGE:
                request = new MessageClientRequest(requestJson.get("content").getAsString());
                break;
            case RequestConstants.QUIT:
                request = new QuitClientRequest();
                break;
            default:
                throw new JsonParseException("Unexpected request type");
        }
        return request;
    }
}
