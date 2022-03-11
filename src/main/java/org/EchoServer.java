//package org;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import org.handlers.EchoServerHandler;
//
//import java.net.InetSocketAddress;
//
//public class EchoServer {
//    private final int port;
//
//
//    public EchoServer(int port) {
//        this.port = port;
//    }
//
//    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
//        }
//
//        int port = Integer.parseInt(args[0]);
//        new EchoServer(port).start();
//    }
//
//    public void start() throws Exception {
//        final EchoServerHandler serverHandler = new EchoServerHandler();
//        EventLoopGroup group = new NioEventLoopGroup();
//
//        try{
//            ServerBootstrap bootstrap = new ServerBootstrap();
//            bootstrap.group(group)
//                    .channel(NioServerSocketChannel.class) // Specifies the use of an NIO transport Channel
//                    .localAddress(new InetSocketAddress(port)) // set socket address using port
//                    .childHandler(new ChannelInitializer<SocketChannel>() { // add echoserverhandler to the chnnels channelpipleline
//                        @Override
//                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            // echoServerHandler is @shareable hence use same one
//                            socketChannel.pipeline().addLast(serverHandler);
//                        }
//                    });
//            // bind the server async
//            // sync() awaits for the bind to complete
//            ChannelFuture future = bootstrap.bind().sync();
//            // get the CloseFuture of the channel and blocks the current thred until its complete
//            future.channel().closeFuture().sync();
//        }
//        finally {
//            // shutdown and release all resources
//            group.shutdownGracefully().sync();
//        }
//    }
//}
