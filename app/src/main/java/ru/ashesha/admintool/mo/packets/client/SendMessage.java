package ru.ashesha.admintool.mo.packets.client;

import ru.ashesha.admintool.mo.packets.Packet;

;

public class SendMessage extends Packet {

    final boolean t, Bot;
    final String author, PlayerName;
    final String colorNick, message;
    final String room;

    public SendMessage(String author) {
        this.t = false;
        Bot = false;
        this.author = author;
        PlayerName = author;
        this.colorNick = "[#ffff80]";
        this.message = "У меня нет идей, кто это может быть!";
        this.room = "Частная классика";
    }
}
