package ru.ashesha.admintool.mo.packets.client;

import org.json.JSONArray;
import ru.ashesha.admintool.mo.packets.Packet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnlineFriend extends Packet {

    final List<String> onlines = new ArrayList<>();

    public OnlineFriend(String... onlines) {
        this.onlines.addAll(Arrays.asList(onlines));
    }

    public void addNick(String nick) {
        onlines.add(nick);
    }

    @Override
    public Object convertToJSON() {

        JSONArray ignored = new JSONArray();
        for (String online : onlines)
            ignored.put(online);
        return ignored;

    }
}
