package distributed.chat.server.service.client;

import distributed.chat.server.model.message.request.client.ListClientRequest;
import distributed.chat.server.model.message.response.client.RoomListClientResponse;
import distributed.chat.server.states.ServerState;

import java.util.ArrayList;

public class ListService extends AbstractClientService<ListClientRequest, RoomListClientResponse> {

    private static ListService instance;

    private ListService() {
    }

    public static synchronized ListService getInstance() {
        if (instance == null) {
            instance = new ListService();
        }
        return instance;
    }

    @Override
    public void processRequest(ListClientRequest request) {
        System.out.println("ListService : process request");
        // global room set to arraylist
        ArrayList<String> globalRoomList = new ArrayList<>();
        globalRoomList.addAll(ServerState.globalRooms.keySet());
        System.out.println("globalRoomList " + globalRoomList.size());
        for (int i = 0; i < globalRoomList.size(); i++) {
            System.out.println(globalRoomList.get(i));
        }
        // response obj
        RoomListClientResponse roomListClientResponse = new RoomListClientResponse(globalRoomList);
        sendResponse(roomListClientResponse, request.getSender());

        System.out.println("response : " + roomListClientResponse);
    }
}
