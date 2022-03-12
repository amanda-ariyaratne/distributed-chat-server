package distributed.chat.server.bootstrap.client;

import distributed.chat.server.bootstrap.initializers.ServerAsClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class ServerAsClient {
    private final String host;
    private final int port;
    private Channel channel;

    public ServerAsClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ServerAsClientInitializer());
            ChannelFuture f = b.connect().sync();
            this.channel = f.channel();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public Channel getChannel() {
        return channel;
    }
}
