package ru.ashesha.admintool.mo.packets.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ashesha.admintool.mo.packets.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

public class getClansTopResult extends Packet {
    public List<String[]> clans = new ArrayList<>();
    @Override
    protected void parsePacket(JSONArray array) throws JSONException {
        for (int i = 0; i < array.length(); i ++) {
            JSONObject object = array.getJSONObject(i);
            if (object.getInt("NumberPlayers") <= 0)
                continue;

            if (object.getInt("Lvl") == 0 && object.getInt("NumberPlayers") <= 10 && object.getInt("MaxPlayers") == 30 &&
                    object.getInt("HonorEarnedInTerritory") == 0 && object.getInt("Honor") == 0 &&
                    object.getString("Items").isEmpty() &&
                    object.getString("Territory").isEmpty() && object.getString("RegistrateOnTerritory").isEmpty())
                continue;

            String territory = object.getString("Territory").toLowerCase().replace("район", "").replace(" ", "");
            String kv = object.getString("RegistrateOnTerritory").toLowerCase().replace("район", "").replace(" ", "");

            String[] clan = {object.getString("Name"), object.getString("Leader"), object.getString("Lvl"),
                    object.getString("NumberPlayers") + "/" + object.getString("MaxPlayers"),
                    object.getString("HonorEarnedInTerritory") + "; " + object.getString("Honor"),
                    object.getString("Items"), territory, kv};

            clans.add(clan);
        }
    }
}
