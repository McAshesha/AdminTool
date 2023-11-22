package ru.ashesha.admintool.mo.packets.server;

import org.json.JSONArray;
import org.json.JSONException;
import ru.ashesha.admintool.mo.packets.Packet;

;

public class JoinRoom extends Packet {

    public int count;

    @Override
    protected void parsePacket(JSONArray array) throws JSONException {
        count = array.getInt(0);
    }
}
