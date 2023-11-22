package ru.ashesha.admintool.mo.packets.client;


import ru.ashesha.admintool.mo.packets.Packet;

;

public class OutFromClan extends Packet {
    final String memberNick, clan;

    public OutFromClan(String nick, String clan) {

        this.memberNick = nick;
        this.clan = clan;

    }

}
