package distributed.chat.server.handlers.server;

import distributed.chat.server.service.election.ElectionService;
import distributed.chat.server.states.ServerState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;

import java.net.SocketException;
import java.util.Map;

/***
 * Inbound Handler for handling exception
 */
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("WARN: Server Channel inactive detected");
        for (Map.Entry<String, Channel> server : ServerState.serverChannels.entrySet()) {
            Channel c = ServerState.serverChannels.get(server.getKey());
            if (c.equals(ctx.channel())) {
                ServerState.serverChannels.remove(server.getKey());
                System.out.println("WARN: " + server.getKey() + " channel closed");

                if (server.getKey().equals(ServerState.leaderId)) {
                    System.out.println("INFO: " + server.getKey() + " was the coordinator");
                    ElectionService electionService = ElectionService.getInstance();
                    electionService.startElection();
                }
                break;
            }
        }

        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof SocketException) {
            ctx.close();
        } else if (cause instanceof CorruptedFrameException) {}
        else {
            cause.printStackTrace();
        }
    }
}
