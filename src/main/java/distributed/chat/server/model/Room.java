package distributed.chat.server.model;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String roomId;
    private Client owner;
    private Set<Client> members;

    public Room(String roomId, Client owner) {
        this.roomId = roomId;
        this.owner = owner;
        this.members = new HashSet<>();
        this.members.add(owner);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Set<Client> getMembers() {
        return members;
    }

    public void setMembers(Set<Client> members) {
        this.members = members;
    }

    public synchronized boolean addMember(Client client) {
        if (!members.contains(client)) {
            members.add(client);
            return true;
        }
        return false;
    }

    public  synchronized boolean removeMember(Client client){
        if (members.contains(client)) {
            members.remove(client);
            return true;
        }
        return false;
    }
}