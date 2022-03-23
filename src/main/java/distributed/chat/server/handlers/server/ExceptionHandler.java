package distributed.chat.server.handlers.server;

import distributed.chat.server.service.election.ElectionService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;

import java.util.Map;

public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
            Channel c = ServerState.serverChannels.get(server.getKey());
            if (c.equals(ctx.channel())) {
                c.close();
                ServerState.serverChannels.remove(server.getKey());
                System.out.println(server.getKey() + " channel closed");
                // TODO : stop write requests
            }
            if (server.getKey().equals(ServerState.leaderId)) {
                System.out.println(server.getKey() + " was the coordinator.");
                ElectionService electionService = ElectionService.getInstance();
                electionService.startElection();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }
}
