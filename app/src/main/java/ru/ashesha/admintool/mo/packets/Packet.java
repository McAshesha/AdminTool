package ru.ashesha.admintool.mo.packets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public abstract class Packet {


    public static <T extends Packet> T parsePacket(Class<T> packetClass, JSONObject json) {

        try {

            T packet = packetClass.newInstance();
            Field[] fields = packetClass.getDeclaredFields();

            for (Field field : fields) {

                try {

                    field.setAccessible(true);
                    field.set(packet, json.get(field.getName()));

                } catch (Throwable ignored) {
                }

            }

            return packet;

        } catch (Throwable ignored) {

            return null;

        }

    }

    public static <T extends Packet> T parsePacket(Class<T> packetClass, JSONArray json) {

        try {

            T packet = packetClass.newInstance();

            Field[] fields = packetClass.getDeclaredFields();

            if (fields.length == 0)
                return packet;

            packet.parsePacket(json);

            return packet;

        } catch (Throwable ignored) {

            return null;

        }

    }

    protected void parsePacket(JSONArray array) throws JSONException, IllegalAccessException {

        JSONObject json = array.getJSONObject(0);
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {

            try {

                field.setAccessible(true);
                field.set(this, json.get(field.getName()));

            } catch (Throwable ignored) {
            }

        }

    }

    public Object convertToJSON() {

        JSONObject json = new JSONObject();

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {

            try {

                field.setAccessible(true);
                json.put(field.getName(), field.get(this));

            } catch (Throwable ignored) {
            }

        }

        return json;

    }

    public final String getName() {
        return this.getClass().getSimpleName();
    }

}
