package ru.ashesha.admintool.mo.packets.client;

import ru.ashesha.admintool.mo.packets.Packet;

;

public class JoinServer extends Packet {

    final boolean potion;
    final int role;
    final int dislikeHave, like, percentWin;
    final String room;
    final String skinRole, skinShirt;
    final int mmr, money, medal, hat, VIP;
    final String name;

    public JoinServer(String name) {
        this.potion = true;
        this.role = 2;
        this.dislikeHave = 0;
        this.like = 127;
        this.percentWin = 100;
        this.room = "Частная классика";
        this.skinRole = "mirn.jpg";
        this.skinShirt = "Cart.jpg";
        this.mmr = 1;
        this.money = 0;
        this.medal = 0;
        this.hat = 0;
        this.VIP = 2;
        this.name = name;
    }
}
