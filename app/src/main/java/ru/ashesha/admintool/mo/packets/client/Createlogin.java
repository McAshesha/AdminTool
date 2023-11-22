package ru.ashesha.admintool.mo.packets.client;

import ru.ashesha.admintool.mo.packets.Packet;

public class Createlogin extends Packet {

    final int FirstMMR, FirstMoney;
    final String login, password, mail, i;

    public Createlogin(String login, String password) {

        this.login = login;
        this.password = password;
        this.FirstMMR = 0;
        this.FirstMoney = 0;
        this.mail = "";
        this.i = "d";

    }

}
