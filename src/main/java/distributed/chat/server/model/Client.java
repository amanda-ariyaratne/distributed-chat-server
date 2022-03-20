package distributed.chat.server.model;

import io.netty.channel.ChannelHandlerContext;

public class Client {
    private String identity;
    private ChannelHandlerContext ctx;
    private Room room;
    private boolean already_room_owner;

    public Client(ChannelHandlerContext ctx){
        this.ctx = ctx;
        already_room_owner = false;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public boolean isAlready_room_owner() {
        return already_room_owner;
    }

    public void setAlready_room_owner(boolean already_room_owner) {
        this.already_room_owner = already_room_owner;
    }
}
