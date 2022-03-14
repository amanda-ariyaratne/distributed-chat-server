package distributed.chat.server.states;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerState {

    public static String leaderId;
    public static Integer leaderPort;
    public static String localId;
    public static Integer localPort;

    public static Map<ChannelId, Client> activeClients = new ConcurrentHashMap<>();

    public static Map<String, Client> localClients = new ConcurrentHashMap<>();
    public static Set<String> globalClients = new HashSet<String>();
    public static Map<String, Client> reservedClients = new ConcurrentHashMap<>();

    public static Map<String, Room> rooms = new ConcurrentHashMap<>();
    public static Map<String, Channel> serverChannels = new ConcurrentHashMap<>();

}
