package ru.ashesha.admintool.mo.packets.client;


import org.json.JSONArray;
import org.json.JSONException;
import ru.ashesha.admintool.mo.packets.Packet;

;

public class CreateServer extends Packet {

    final boolean LowRate,  potion,  maniacWithBandage,  Inc;
    final int dislikeHave;
    final String skinRole,  skinShirt,  name,  pass;
    final int mmr;
    final int numberMafia;
    final int medal;
    final int numberPlayer;
    final int percentWin;
    final int hat;
    final int Mod;
    final int like;
    final int PlayerRole;
    final int game_style;
    final int money;
    final int numberPlayersInRoom;
    final int VIP;
    final JSONArray RoleNeed;



    public CreateServer() {

        this.name = "Частная классика";
        this.pass = "PrivateClassikForFriend";
        LowRate = false;
        dislikeHave = 0;
        skinRole = "mirn.jpg";
        mmr = 1;
        numberMafia = 1;
        medal = 0;
        numberPlayer = 10;
        percentWin = 100;
        hat = 0;
        potion = true;
        Mod = 0;
        like = 127;
        PlayerRole = 2;
        maniacWithBandage = false;
        game_style = 0;
        money = 0;
        numberPlayersInRoom = 1;
        skinShirt = "Cart.jpg";
        VIP = 2;
        Inc = false;
        try {
            RoleNeed = new JSONArray("[2, 2, 2, 2, 2, 2, 2, 2, 2, 2]");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}