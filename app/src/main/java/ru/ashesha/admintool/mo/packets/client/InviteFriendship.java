package ru.ashesha.admintool.mo.packets.client;

import ru.ashesha.admintool.mo.packets.Packet;

public class InviteFriendship extends Packet {

    final String friend, name;

    public InviteFriendship(String name, String friend) {
        this.friend = friend;
        this.name = name;
    }
}
