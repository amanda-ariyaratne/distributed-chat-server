package distributed.chat.server;

import distributed.chat.server.bootstrap.server.ServerAsClient;
import distributed.chat.server.bootstrap.client.ServerToClient;
import distributed.chat.server.bootstrap.server.ServerToServer;
import distributed.chat.server.model.ServerConfig;
import distributed.chat.server.states.ServerState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length != 4) {
            System.out.println("Command line arguments are missing");
            return;
        }

        String serverId = "";
        String configFile = "";
        if (args[0].equals("-i") || args[2].equals("-c")) {
            serverId = args[1];
            configFile = args[3];
        } else if (args[2].equals("-i") || args[0].equals("-c")) {
            serverId = args[3];
            configFile = args[1];
        } else {
            System.out.println("Command line arguments are incorrect");
            return;
        }

        Map<String, ServerConfig> servers = new HashMap<>();

        try {
            servers = readServerConfgis(configFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception occurred");
            return;
        }

        int portServerToClient = servers.get(serverId).getClients_port();
        int portServerToServer = servers.get(serverId).getCoordination_port();

        String finalServerId = serverId;
        Thread coordinatorThread = new Thread(() -> {
            try {
                new ServerToServer(portServerToServer, finalServerId).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        coordinatorThread.start();

        Thread clientThread = new Thread(() -> {
            try {
                new ServerToClient(portServerToClient, finalServerId).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        clientThread.start();

        for (Map.Entry<String, ServerConfig> server : servers.entrySet()) {
            ServerConfig configs = server.getValue();

            if (portServerToServer != configs.getCoordination_port()) {
                new Thread(() -> {
                    try {
                        ServerAsClient serverAsClient = new ServerAsClient(
                                configs.getServer_address(),
                                configs.getCoordination_port()
                        );
                        serverAsClient.start();
                        ServerState.serverChannels.put(configs.getServer_id(), serverAsClient.getChannel());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }

    }

    private static Map<String, ServerConfig> readServerConfgis(String configFile) throws IOException {
        Map<String, ServerConfig> servers = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        try {
            String line;
            String ls = System.getProperty("line.separator");

            while((line = reader.readLine()) != null) {
                Scanner s = new Scanner(line).useDelimiter("\\t");
                String serverId = s.next();
                ServerConfig server = new ServerConfig(serverId, s.next(), s.nextInt(), s.nextInt());
                servers.put(serverId, server);
            }
            return servers;
        } finally {
            reader.close();
        }
    }
}
