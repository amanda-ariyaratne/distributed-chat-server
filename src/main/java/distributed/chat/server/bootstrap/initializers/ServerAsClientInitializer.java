package distributed.chat.server.bootstrap.initializers;

import distributed.chat.server.handlers.election.*;
import distributed.chat.server.handlers.heartbeat.HeartBeatHandler;
import distributed.chat.server.handlers.server.*;
import distributed.chat.server.helper.MessageDecoder;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class ServerAsClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("json_decode", new JsonObjectDecoder());
        pipeline.addLast("string_encode", new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast("pojo_decode", new MessageDecoder());
        pipeline.addLast("sync_global_lists", new SyncGlobalListsHandler());
        pipeline.addLast("reserve_identity_confirm", new ReserveIdentityConfirmHandler());
        pipeline.addLast("reserve_room_confirm", new ReserveRoomConfirmHandler());

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

        pipeline.addLast("exception", new ExceptionHandler());
    }
}
