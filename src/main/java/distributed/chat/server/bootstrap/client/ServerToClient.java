package distributed.chat.server.bootstrap.client;

import distributed.chat.server.bootstrap.initializers.ServerToClientInitializer;
import distributed.chat.server.model.Room;
import distributed.chat.server.states.ServerState;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class ServerToClient {
    private final int port;

    public ServerToClient(int port, String id) {
        this.port = port;
        ServerState.localId = id;
        ServerState.localPort = port;
        ServerState.localRooms.put("MainHall-" + id, new Room("MainHall-" + id, null));
        ServerState.globalRooms.put(
                "MainHall-" + id,
                ServerState.localId
        );

    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ServerToClientInitializer());
            ChannelFuture f = b.bind().sync();
            System.out.println("Listening on service port " + port);
            f.channel().closeFuture().sync();
        } finally {
            System.out.println("Shutting down");
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }
}
