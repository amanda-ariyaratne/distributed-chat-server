package distributed.chat.server.states;

import distributed.chat.server.model.Client;
import distributed.chat.server.model.Room;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerState {
    public static String leaderId = "s1";
    public static Map<ChannelId, Client> activeClients = new ConcurrentHashMap<>();
    public static Map<String, Client> clients = new ConcurrentHashMap<>();
    public static Map<String, Client> reservedClients = new ConcurrentHashMap<>();
    public static Map<String, Room> rooms = new ConcurrentHashMap<>();
    public static Map<String, Channel> serverChannels = new ConcurrentHashMap<>();
}
