package distributed.chat.server;

import distributed.chat.server.bootstrap.client.ServerToClient;
import distributed.chat.server.bootstrap.server.ServerAsClient;
import distributed.chat.server.bootstrap.server.ServerToServer;
import distributed.chat.server.model.ServerConfig;
import distributed.chat.server.service.election.IAmUpService;
import distributed.chat.server.states.ServerState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        if (args.length != 4) {
            System.out.println(ServerState.localId + " WARN: Command line arguments are missing");
            return;
        }

        String serverId;
        String configFile;
        if (args[0].equals("-i") || args[2].equals("-c")) {
            serverId = args[1];
            configFile = args[3];
        } else if (args[2].equals("-i") || args[0].equals("-c")) {
            serverId = args[3];
            configFile = args[1];
        } else {
            System.out.println(ServerState.localId + " WARN: Command line arguments are incorrect");
            return;
        }

        Map<String, ServerConfig> servers;

        try {
            servers = readServerConfgis(configFile);
            ServerState.serverConfig = servers.get(serverId);
            ServerState.servers = servers;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(ServerState.localId + " WARN: Command line arguments are missing");
            return;
        }

        int portServerToClient = servers.get(serverId).getClients_port();
        int portServerToServer = servers.get(serverId).getCoordination_port();

        ServerState.localId = serverId;
        ServerState.leaderId = serverId;
        ServerState.leaderPort = portServerToServer;
        ServerState.localServerPort = portServerToServer;
        ServerState.localClientPort = portServerToClient;

        String finalServerId = serverId;
        Thread coordinatorThread = new Thread(() -> {
            try {
                new ServerToServer(portServerToServer, finalServerId).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        coordinatorThread.start();

        for (Map.Entry<String, ServerConfig> server : servers.entrySet()) {
            ServerConfig configs = server.getValue();

            if (!serverId.equals(configs.getServer_id())) {
                new Thread(() -> {
                    try {
                        ServerAsClient serverAsClient = new ServerAsClient(
                                configs.getServer_id(),
                                configs.getServer_address(),
                                configs.getCoordination_port()
                        );
                        System.out.println(ServerState.localId + " INFO: " + "Trying to connect to " + configs.getServer_id() + " on port " + configs.getCoordination_port());
                        serverAsClient.start();

                    } catch (Exception e) {
                        System.out.println(ServerState.localId + " WARN: " + "Connection failed for " + configs.getServer_id());
                    } finally {
                        ServerState.serverAsClientThreadCount.getAndIncrement();
                    }
                }).start();
            }
        }

        while (true) {
            if (ServerState.serverAsClientThreadCount.get() == servers.size()) {
                IAmUpService iAmUpService = IAmUpService.getInstance();
                iAmUpService.broadcastIAmUpMessage();
                break;
            }
        }

        Thread clientThread = new Thread(() -> {
            try {
                new ServerToClient(portServerToClient, finalServerId).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        clientThread.start();
    }

    private static Map<String, ServerConfig> readServerConfgis(String configFile) throws IOException {
        Map<String, ServerConfig> servers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try (Scanner s = new Scanner(line)) {
                    s.useDelimiter("\\s");
                    String serverId = s.next();

                    ServerConfig server = new ServerConfig(serverId, s.next(), s.nextInt(), s.nextInt());
                    servers.put(serverId, server);
                }
            }
            return servers;
        }
    }
}
