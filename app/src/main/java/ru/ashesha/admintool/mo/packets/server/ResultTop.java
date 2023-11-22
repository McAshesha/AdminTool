package ru.ashesha.admintool.mo.packets.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ashesha.admintool.mo.packets.Packet;

import java.util.*;

public class ResultTop extends Packet {

    public List<Map.Entry<String, Integer>> top = new ArrayList<>();

    @Override
    protected void parsePacket(JSONArray array) throws JSONException {

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            top.add(new AbstractMap.SimpleEntry<>(object.getString("login"), object.getInt("mmr")));
        }

    }
}
