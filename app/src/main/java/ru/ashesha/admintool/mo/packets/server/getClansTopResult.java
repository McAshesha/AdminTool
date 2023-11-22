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
    public List<Map<String, String>> clans;
    @Override
    protected void parsePacket(JSONArray array) throws JSONException, IllegalAccessException {

        clans = new ArrayList<>();

        for (int i = 0; i < array.length(); i ++) {
            Map<String, String> map = new HashMap<>();
            JSONObject object = array.getJSONObject(i);
            map.put("Name", object.getString("Name"));
            map.put("Leader", object.getString("Leader"));
            clans.add(map);
        }

    }
}
