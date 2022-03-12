package distributed.chat.server.model;

public class ServerConfig {
    private String server_id;
    private String server_address;
    private int clients_port;
    private int coordination_port;

    public ServerConfig(String server_id, String server_address, int clients_port, int coordination_port) {
        this.server_id = server_id;
        this.server_address = server_address;
        this.clients_port = clients_port;
        this.coordination_port = coordination_port;
    }

    public String getServer_id() {
        return server_id;
    }

    public String getServer_address() {
        return server_address;
    }

    public int getClients_port() {
        return clients_port;
    }

    public int getCoordination_port() {
        return coordination_port;
    }
}
