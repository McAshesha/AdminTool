package ru.ashesha.admintool.mo.packets.server;

import org.json.JSONArray;
import ru.ashesha.admintool.mo.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class ListOfServers extends Packet {

    public List<String> names;

    @Override
    protected void parsePacket(JSONArray array) {

        names = new ArrayList<>();
        for (int i = 0; i < array.length(); i ++) {

            try {

                names.add(array.getJSONObject(i).getString("id"));

            } catch (Throwable ignored) {}

        }

    }
}
