package distributed.chat.server.handlers.heartbeat;

import distributed.chat.server.model.message.heartbeat.HeartBeatMessage;
import distributed.chat.server.service.election.ElectionService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Map;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) {
        if (evt instanceof IdleStateEvent) {
            final IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
                    Channel c = ServerState.serverChannels.get(server.getKey());
                    if (c.equals(ctx.channel())) {
                        c.close();
                        ServerState.serverChannels.remove(server.getKey());
                        // TODO : stop write requests
                    }
                    if (server.getKey().equals(ServerState.leaderId)) {
                        ElectionService electionService = ElectionService.getInstance();
                        electionService.startElection();
                    }
                }
            } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                HeartBeatMessage message = new HeartBeatMessage();
                ctx.writeAndFlush(message.toString());
            }
        }
    }

}
