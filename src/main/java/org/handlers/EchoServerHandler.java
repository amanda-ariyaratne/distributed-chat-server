package org.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable // can be shared by multiple channels
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    // Called for each incoming message
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        // Writes the received message to the sender without flushing the outbound messages
        ctx.write(in);
    }

    // Notifies the handler that the last call made to channelRead() was the last message in the current batch
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // Flushes pending  messages to the remote peer and closes the channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // close channel
        ctx.close();
    }
}
