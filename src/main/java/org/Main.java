package org;

import org.services.CoordinationServer;


public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println("main started");
        String serverId = "s1";
        String serverConfig;

        Thread coordinatorThread = new Thread(() -> {
            try {
                CoordinationServer.getInstance().run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        coordinatorThread.start();

    }
}
