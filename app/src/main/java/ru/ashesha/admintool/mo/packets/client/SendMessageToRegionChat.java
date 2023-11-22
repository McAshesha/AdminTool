package ru.ashesha.admintool.mo.packets.client;

import ru.ashesha.admintool.mo.packets.Packet;

public class SendMessageToRegionChat extends Packet {

    final int MMR;
    final String author, partyName, colorNick, message;

    public SendMessageToRegionChat(String message, String color) {

        this.message = message;
        this.colorNick = color;
        this.MMR = 0;
        this.author = "";
        this.partyName = "";

    }

}
