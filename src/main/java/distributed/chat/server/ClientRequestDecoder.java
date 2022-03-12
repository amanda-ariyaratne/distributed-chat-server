package distributed.chat.server;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import distributed.chat.server.model.message.request.client.AbstractClientRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class ClientRequestDecoder extends ByteToMessageDecoder {

    private final Gson gson;

    public ClientRequestDecoder() {
        super();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.registerTypeAdapter(AbstractClientRequest.class, new ClientRequestDeserializer());
        gson = gsonBuilder.create();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readableBytes();
        String json = byteBuf.readCharSequence(length, CharsetUtil.UTF_8).toString();
        list.add(gson.fromJson(json, AbstractClientRequest.class));
    }
}
