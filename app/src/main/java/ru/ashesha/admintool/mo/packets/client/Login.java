package ru.ashesha.admintool.mo.packets.client;


import ru.ashesha.admintool.mo.packets.Packet;
import ru.ashesha.admintool.utils.Device;

;

public class Login extends Packet {

    final String login, password, version, i, o,
            p1, p2, m1, m2, steamId;
    final boolean me, color, isUsePassword;


    public Login(String login, String password, String version) {
        Device device = Device.getInstance();
        this.login = login;
        this.password = password;
        this.version = version;
        this.me = false;
        this.color = false;
        this.isUsePassword = true;
        this.o = "2068136186";
        this.i = device.getLoginI();
        this.m1 = device.getLoginM1();
        this.m2 = device.getLoginM2();
        this.p1 = device.getLoginP1();
        this.p2 = device.getLoginP2();
        this.steamId = "";

    }

}
