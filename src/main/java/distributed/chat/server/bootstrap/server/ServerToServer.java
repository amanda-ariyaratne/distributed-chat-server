package distributed.chat.server.bootstrap.server;

import distributed.chat.server.bootstrap.initializers.ServerToServerInitializer;
import distributed.chat.server.states.ServerState;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class ServerToServer {
    private final int port;
    private final String id;

    public ServerToServer(int port, String id) {
        this.port = port;
        this.id = id;
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
                    .childHandler(new ServerToServerInitializer());
            ChannelFuture f = b.bind().sync();
            ServerState.serverAsClientThreadCount.getAndIncrement();
            System.out.println("Listening on management port " + port);
            f.channel().closeFuture().sync();
        } finally {
            System.out.println("Shutting down");
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    public String getId() {
        return id;
    }
}
