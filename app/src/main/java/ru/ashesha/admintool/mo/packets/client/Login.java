package ru.ashesha.admintool.mo.packets.client;


import ru.ashesha.admintool.mo.packets.Packet;
import ru.ashesha.admintool.utils.Device;

public class Login extends Packet {

    final String login, password, version, i, o,
            p1, p2, m1, m2, steamId;
    final boolean me, color, isUsePassword;


    public Login(String login, String password, String version) {
        this.login = login;
        this.password = password;
        this.version = version;
        this.me = false;
        this.color = false;
        this.isUsePassword = true;
        this.o = "2068136186";
        /*Device device = Device.getInstance();
        this.i = device.getLoginI();
        this.m1 = device.getLoginM1();
        this.m2 = device.getLoginM2();
        this.p1 = device.getLoginP1();
        this.p2 = device.getLoginP2();*/
        this.i = "3a21e098c5e64ee2 ! com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME ! null ! http://www.google.com http://www.google.co.uk ! DSDGS-KM20 ! PRO-KM20 ! unknown";
        this.m1 = "08:43:27:C7:E6:7F";
        this.m2 = "";
        this.p1 = "172.16.64.15";
        this.p2 = "FE80::SDG:27FF:FE1C:607F";
        this.steamId = "";

    }

}
