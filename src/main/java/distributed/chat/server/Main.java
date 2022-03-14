package distributed.chat.server;

import distributed.chat.server.bootstrap.server.ServerAsClient;
import distributed.chat.server.bootstrap.client.ServerToClient;
import distributed.chat.server.bootstrap.server.ServerToServer;
import distributed.chat.server.model.ServerConfig;
import distributed.chat.server.states.ServerState;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int portServerToClient = 4444;
        String idLocal = "s1";
        int portServerToServer = 5555;

        Map<String, ServerConfig> servers = new HashMap<>();
        servers.put("s1", new ServerConfig(
                "s1",
                "localhost",
                4444,
                5555));
        servers.put("s2", new ServerConfig(
                "s2",
                "localhost",
                4445,
                5556));
        servers.put("s3", new ServerConfig(
                "s3",
                "localhost",
                4446,
                5557));

        // Todo: Read these data from config files

        for (Map.Entry<String, ServerConfig> server : servers.entrySet()){
            ServerConfig configs = server.getValue();

            if (portServerToServer != configs.getCoordination_port()) {
                new Thread(() -> {
                    try {
                        ServerAsClient serverAsClient = new ServerAsClient(
                                configs.getServer_address(),
                                configs.getCoordination_port()
                        );
                        serverAsClient.start();
                        ServerState.serverChannels.put(configs.getServer_id() , serverAsClient.getChannel());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }

        Thread coordinatorThread = new Thread(() -> {
            try {
                new ServerToServer(portServerToServer, idLocal).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        coordinatorThread.start();

        try {
            new ServerToClient(portServerToClient, idLocal).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
