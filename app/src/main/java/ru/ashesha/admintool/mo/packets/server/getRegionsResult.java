package ru.ashesha.admintool.mo.packets.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ashesha.admintool.mo.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class getRegionsResult extends Packet {
    public List<JSONObject> regions = new ArrayList<>();
    @Override
    protected void parsePacket(JSONArray array) throws JSONException {
        for (int i = 0; i < array.length(); i ++)
            regions.add(array.getJSONObject(i));
    }
}
