package distributed.chat.server.bootstrap.initializers;

import distributed.chat.server.handlers.election.*;
import distributed.chat.server.handlers.heartbeat.HeartBeatHandler;
import distributed.chat.server.helper.MessageDecoder;
import distributed.chat.server.handlers.server.ReserveIdentityHandler;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class ServerToServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        //Custom handler
        pipeline.addLast("json_decode", new JsonObjectDecoder());
        pipeline.addLast("string_encode", new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast("pojo_decode", new MessageDecoder());
        pipeline.addLast("reserve_identity", new ReserveIdentityHandler());
        pipeline.addLast(new IdleStateHandler(
                ServerState.heartBeatReadTimeout,
                ServerState.heartBeatWriteTimeout,
                0));
        pipeline.addLast("heartbeat", new HeartBeatHandler());
        pipeline.addLast(
                new AnswerHandler(),
                new CoordinatorHandler(),
                new ElectionHandler(),
                new IAmUpHandler(),
                new NominationHandler(),
                new ViewHandler()
        );
    }
}
