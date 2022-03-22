package distributed.chat.server.bootstrap.initializers;

import distributed.chat.server.handlers.client.*;
import distributed.chat.server.handlers.election.*;
import distributed.chat.server.handlers.heartbeat.HeartBeatHandler;
import distributed.chat.server.handlers.server.ExceptionHandler;
import distributed.chat.server.helper.MessageDecoder;
import distributed.chat.server.states.ServerState;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class ServerToClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("json_decode", new JsonObjectDecoder());
        pipeline.addLast("string_encode", new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast("pojo_decode", new MessageDecoder());
        pipeline.addLast("new_identity", new NewIdentityHandler());
        pipeline.addLast("create_room",new CreateRoomHandler());
        pipeline.addLast("join_room",new JoinRoomHandler());
        pipeline.addLast("move_join",new MoveJoinHandler());
        pipeline.addLast("delete_room",new DeleteRoomHandler());
        pipeline.addLast("list",new ListHandler());
        pipeline.addLast("who",new WhoHandler());
        pipeline.addLast("message", new MessageHandler());
        pipeline.addLast("quit", new QuitHandler());
        pipeline.addLast("exception", new ExceptionHandler());
    }
}
