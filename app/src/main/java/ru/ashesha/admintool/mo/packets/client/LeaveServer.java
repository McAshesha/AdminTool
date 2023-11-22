package ru.ashesha.admintool.mo.packets.client;


import ru.ashesha.admintool.mo.packets.Packet;

;

public class LeaveServer extends Packet {

    final String id;

    public LeaveServer() {
        this.id = "Частная классика";
    }

}
