package ru.ashesha.admintool.mo.packets.client;

import ru.ashesha.admintool.mo.packets.Packet;

public class MessageToFriend extends Packet {

    final String friend, name, text;

    public MessageToFriend(String name, String friend, String text) {
        this.friend = friend;
        this.name = name;
        this.text = text;
    }
}
