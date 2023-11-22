package ru.ashesha.admintool.mo;


import ru.ashesha.admintool.mo.packets.Packet;

;

public interface PacketListener<T extends Packet> {

    void call(T packet);

}
