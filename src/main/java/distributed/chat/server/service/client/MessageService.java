package distributed.chat.server.service.client;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import distributed.chat.server.model.message.request.client.MessageClientRequest;
import distributed.chat.server.model.message.response.client.MessageClientResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class MessageService extends AbstractClientService<MessageClientRequest, MessageClientResponse> {

    private static MessageService instance;

    private MessageService() {
    }

    public static synchronized MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    @Override
    public void processRequest(MessageClientRequest request) {
        System.out.println("MessageService : processRequest");
        String content = request.getContent();
        String identity = request.getSender().getIdentity();
        Room room = request.getSender().getRoom();
        // broadcast response
        MessageClientResponse messageClientResponse = new MessageClientResponse(content, identity);
        broadcast(messageClientResponse, room, request.getSender());

        System.out.println("broadcast : " + messageClientResponse);
    }

    public void broadcast(MessageClientResponse response, Room room, Client sender) {
        System.out.println("Member length " + room.getMembers().size());
        System.out.println(response.toString());
        for (Client member : room.getMembers()) {
            if (member != sender){
                final ChannelFuture f = member.getCtx().writeAndFlush(response + "\n");
                f.addListener((ChannelFutureListener) future -> {
                    assert f == future;
                });
            }
        }
        System.out.println("finish broadcast");
    }
}
