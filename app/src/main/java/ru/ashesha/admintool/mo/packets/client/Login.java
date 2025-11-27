package ru.ashesha.admintool.mo.packets.client;


import org.json.JSONException;
import org.json.JSONObject;
import ru.ashesha.admintool.mo.packets.Packet;
import ru.ashesha.admintool.utils.Device;

public class Login extends Packet {

    final String login, password, i, o,
            p1, p2, m1, m2, steamId, IM;
    final boolean me, color, isUsePassword;
    final String version;

    final JSONObject sand;


    public Login(String login, String password, String version) {
        this(login, password, version, false);

    }

    public Login(String login, String password, String version, boolean fakeData) {
        this.login = login;
        this.password = password;
        this.version = version;
        this.isUsePassword = true;
        this.color = false;
        this.sand = new JSONObject();
        try {
            sand.put("v", System.currentTimeMillis());
            sand.put("b", System.nanoTime());
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        this.me = false;
        this.o = "186712752";
        this.steamId = "";
        if (fakeData) {
            this.i = "fabf52b175038b51 ! com.touchtype.swiftkey/com.touchtype.KeyboardService ! null ! null ! HUAWEIELS-N39 ! ELS-N39 ! unknown";
            this.m1 = "08:43:27:C7:E6:7F";
            this.m2 = "";
            this.p1 = "172.16.64.15";
            this.p2 = "FE80::SDG:27FF:FE1C:607F";
            this.IM = "957dfa23-608d-4276-a7f5-566cfd7e7521 | android.telephony.TelephonyManager@d473f75";
        } else {
            Device device = Device.getInstance();
            this.i = device.getLoginI();
            this.m1 = device.getLoginM1();
            this.m2 = device.getLoginM2();
            this.p1 = device.getLoginP1();
            this.p2 = device.getLoginP2();
            this.IM = device.getPhoneInfo();
        }
    }

}
