package distributed.chat.server.model;

import java.util.ArrayList;

public class Room {
    private String roomId;
    private Client owner;
    private ArrayList<Client> members;

    public Room(String roomId, Client owner) {
        this.roomId = roomId;
        this.owner = owner;
        this.members = new ArrayList<Client>(){
            {
                add(owner);
            }
        };
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

    public ArrayList<Client> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Client> members) {
        this.members = members;
    }
}