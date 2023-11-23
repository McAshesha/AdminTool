package ru.ashesha.admintool.mo.packets.client;


import ru.ashesha.admintool.mo.packets.Packet;

;

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
        this.i = "ee95e826c24031ea ! com.google.android.inputmethod.android.inputmethod.latin.LatinIME ! null ! http://www.google.com http://www.google.co.uk ! REDMINOO-KA20 ! NOO-KA20 ! unknown";
        this.m1 = "1D:6A:AA:13:K0:L4";
        this.m2 = "";
        this.p1 = "192.168.0.189";
        this.p2 = "E0F8::6DCA:F46F:FFED:9538";
        this.steamId = "";

    }

}
