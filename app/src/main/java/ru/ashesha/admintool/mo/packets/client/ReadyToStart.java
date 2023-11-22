package ru.ashesha.admintool.mo.packets.client;

import ru.ashesha.admintool.mo.packets.Packet;

;

public class ReadyToStart extends Packet {

    final String name, id;

    public ReadyToStart(String name) {
        this.name = name;
        this.id = "Частная классика";
    }
}
