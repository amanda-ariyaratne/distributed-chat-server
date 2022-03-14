package distributed.chat.server.helper;

import com.google.gson.*;
import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.request.client.*;
import distributed.chat.server.model.message.request.server.*;
import distributed.chat.server.model.message.response.client.NewIdentityClientResponse;
import distributed.chat.server.model.message.response.server.AddIdentityServerResponse;
import distributed.chat.server.model.message.response.server.ReserveIdentityConfirmServerResponse;
import distributed.chat.server.model.message.response.server.ReserveIdentityServerResponse;

import java.lang.reflect.Type;

public class MessageDeserializer implements JsonDeserializer<AbstractMessage> {

    @Override
    public AbstractMessage deserialize(JsonElement jsonElement, Type type,
                                             JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        final JsonObject requestJson = jsonElement.getAsJsonObject();

        AbstractMessage request;

        switch (requestJson.get("type").getAsString()) {
            case RequestConstants.NEW_IDENTITY:
                request = new NewIdentityClientRequest(requestJson.get("identity").getAsString());
                break;

            case RequestConstants.RESERVE_IDENTITY:
                request = new ReserveIdentityServerRequest(requestJson.get("identity").getAsString());
                break;
            case RequestConstants.RESERVE_IDENTITY_RESPONSE:
                request = new ReserveIdentityServerResponse(
                        requestJson.get("identity").getAsString(),
                        requestJson.get("approved").getAsBoolean()
                );
                break;

            case RequestConstants.RESERVE_IDENTITY_CONFIRM:
                request = new ReserveIdentityConfirmServerRequest(
                        requestJson.get("reserved").getAsBoolean(),
                        requestJson.get("identity").getAsString());
                break;
            case RequestConstants.RESERVE_IDENTITY_CONFIRM_RESPONSE:
                request = new ReserveIdentityConfirmServerResponse(
                        requestJson.get("identity").getAsString(),
                        requestJson.get("reserved").getAsBoolean());
                break;

            case RequestConstants.ADD_IDENTITY:
                request = new AddIdentityServerRequest(
                        requestJson.get("identity").getAsString());
                break;
            case RequestConstants.ADD_IDENTITY_RESPONSE:
                request = new AddIdentityServerResponse(
                        requestJson.get("reserved").getAsBoolean());
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
