package ru.ashesha.admintool.mo.packets.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ashesha.admintool.mo.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class getGangsFightResult extends Packet {
    public List<JSONObject> fights = new ArrayList<>();
    @Override
    protected void parsePacket(JSONArray array) throws JSONException {
        for (int i = 0; i < array.length(); i ++)
            fights.add(array.getJSONObject(i));
    }
}
