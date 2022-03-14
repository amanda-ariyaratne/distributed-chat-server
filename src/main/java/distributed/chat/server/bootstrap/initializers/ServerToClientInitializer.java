package distributed.chat.server.bootstrap.initializers;

import distributed.chat.server.helper.MessageDecoder;
import distributed.chat.server.handlers.client.NewIdentityHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ServerToClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        //Custom handler
        pipeline.addLast("json_decode", new JsonObjectDecoder());
        pipeline.addLast("string_encode", new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast("pojo_decode", new MessageDecoder());
        pipeline.addLast("new_identity", new NewIdentityHandler());
    }
}
