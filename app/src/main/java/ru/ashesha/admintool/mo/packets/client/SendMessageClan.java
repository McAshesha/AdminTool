package ru.ashesha.admintool.mo.packets.client;

import ru.ashesha.admintool.mo.packets.Packet;

public class SendMessageClan extends Packet {

    final String author, clan, colorNick, message;

    public SendMessageClan(String message, String clan, String color) {

        this.message = message;
        this.clan = clan;
        this.colorNick = color;
        this.author = "";

    }

}
