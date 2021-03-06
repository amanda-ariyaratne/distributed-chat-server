package distributed.chat.server.model.message.request.server;

import distributed.chat.server.RequestConstants;
import distributed.chat.server.model.message.MessageType;

import java.util.Iterator;
import java.util.Map;

public class SyncGlobalListsServerRequest extends AbstractServerRequest {
    private final String[] clients;
    private final Map<String, String> rooms;

    public SyncGlobalListsServerRequest(String[] clients, Map<String, String> rooms) {
        super(MessageType.SYNC_GLOBAL_LISTS);
        this.clients = clients;
        this.rooms = rooms;
    }

    public String[] getClients() {
        return clients;
    }

    public Map<String, String> getRooms() {
        return rooms;
    }

    @Override
    public String toString() {
        String clientStr = "[";
        for (int i = 0; i < clients.length; i++) {
            clientStr.concat("'" + clients[i] + "'");
            if (i < clients.length - 1) {
                clientStr.concat(",");
            }
        }
        clientStr.concat("]");

        Iterator it = rooms.entrySet().iterator();
        String roomStr = "[";
        while (it.hasNext()) {
            Map.Entry<String, String> room = (Map.Entry)it.next();
            roomStr.concat("{'" + room.getKey() + "'"
                    + ":'" + room.getValue() + "'}");

            if (it.hasNext())
                roomStr.concat(", ");
        }
        roomStr.concat("]");

        return "{" +
                "\"type\" : \"" + RequestConstants.SYNC_GLOBAL_LISTS + "\"" +
                ", \"clients\" : " + clientStr +
                ", \"rooms\" : " + roomStr +
                "}";
    }
}
