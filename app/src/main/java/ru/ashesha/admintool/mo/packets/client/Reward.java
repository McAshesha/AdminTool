package ru.ashesha.admintool.mo.packets.client;


import ru.ashesha.admintool.mo.packets.Packet;

;

public class Reward extends Packet {
    final String name;

    public Reward(String name) {

        this.name = name;
    }
}