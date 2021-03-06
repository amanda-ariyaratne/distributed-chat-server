package distributed.chat.server.handlers.heartbeat;

import distributed.chat.server.model.message.AbstractMessage;
import distributed.chat.server.model.message.heartbeat.HeartBeatMessage;
import distributed.chat.server.service.election.ElectionService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Map;

/***
 * Inbound Handler for handling heartbeats
 */
public class ClientHeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractMessage request = (AbstractMessage) msg;
        if (request instanceof HeartBeatMessage){
            HeartBeatMessage message = (HeartBeatMessage) msg;
            System.out.println("[" + ServerState.localId + "] INFO: received heartbeat " + message);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) {
        if (evt instanceof IdleStateEvent) {
            final IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {

                for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
                    Channel c = ServerState.serverChannels.get(server.getKey());
                    if (c.equals(ctx.channel())) {
                        c.close();
                        System.out.println(ServerState.localId + " WARN: Did not receive heartbeat message");
                        ServerState.serverChannels.remove(server.getKey());
                        System.out.println(ServerState.localId + " INFO: " + server.getKey() + " channel closed");

                        if (server.getKey().equals(ServerState.leaderId)) {
                            System.out.println(ServerState.localId + " WARN: " + server.getKey() + " was the leader");
                            ElectionService electionService = ElectionService.getInstance();
                            electionService.startElection();
                        }
                        break;
                    }
                }
            } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                HeartBeatMessage message = new HeartBeatMessage(ServerState.localId);
                ctx.writeAndFlush(message.toString());
            }
        }
    }

}
