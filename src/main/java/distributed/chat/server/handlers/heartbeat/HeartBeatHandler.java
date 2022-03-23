package distributed.chat.server.handlers.heartbeat;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.heartbeat.HeartBeatMessage;
import distributed.chat.server.model.message.request.client.ListClientRequest;
import distributed.chat.server.service.election.ElectionService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Map;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof HeartBeatMessage){
            HeartBeatMessage message = (HeartBeatMessage) msg;
//            System.out.println("Received heartbeat message " + message);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) {
        if (evt instanceof IdleStateEvent) {
            final IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                System.out.println("Did not receive heartbeat message");
                for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
                    Channel c = ServerState.serverChannels.get(server.getKey());
                    if (c.equals(ctx.channel())) {
                        c.close();
                        ServerState.serverChannels.remove(server.getKey());
                        System.out.println(server.getKey() + " channel closed");
                        // TODO : stop write requests
                    }
                    if (server.getKey().equals(ServerState.leaderId)) {
                        System.out.println("Leader was removed.");
                        ElectionService electionService = ElectionService.getInstance();
                        electionService.startElection();
                    }
                }
            } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                HeartBeatMessage message = new HeartBeatMessage(ServerState.localId);
                ctx.writeAndFlush(message.toString());
            }
        }
    }

}
