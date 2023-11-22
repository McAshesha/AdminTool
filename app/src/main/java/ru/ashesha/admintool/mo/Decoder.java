package ru.ashesha.admintool.mo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Decoder {

    public static int lfm = 0;

    public static Object encode(Object object) {
        return encode(object, 0);
    }

    public static Object decode(Object object) {

        int lfm = Decoder.lfm;
        return decode(object, 0, lfm);

    }


    public static String encode(String fullPacket) {

        try {

            JSONArray array = new JSONArray(fullPacket.substring(2));
            JSONArray result = new JSONArray();

            for (int i = 0; i < array.length(); i++) {

                if (i != 1)
                    result.put(array.get(i));
                else result.put(encode(array.get(1), 0));

            }

            return toUTF8("42" + encode(array, 0));

        } catch (Exception | Error e) {

            return toUTF8(fullPacket);

        }

    }

    public static String decode(String fullPacket, int lfm) {

        try {

            JSONArray array = new JSONArray(fullPacket.substring(2));
            JSONArray result = new JSONArray();

            for (int i = 0; i < array.length(); i++) {

                if (i != 1)
                    result.put(array.get(i));
                else result.put(decode(array.get(1), 0, lfm));

            }

            return toUTF8("42" + decode(array, 0, lfm));

        } catch (Exception | Error e) {

            return toUTF8(fullPacket);

        }

    }


    @SuppressWarnings("rawtypes")
    private static Object decode(Object var0, int var1, int lfm) {
        try {
            if (var0 != null) {
                if (var0.getClass().equals(JSONArray.class)) {
                    for (int var9 = 0; var9 < ((JSONArray) var0).length(); ++var9) {
                        if (!((JSONArray) var0).get(var9).getClass().equals(JSONObject.class) && !((JSONArray) var0).get(var9).getClass().equals(JSONArray.class)) {
                            ((JSONArray) var0).put(var9, d(((JSONArray) var0).get(var9), var1));
                        } else {
                            decode(((JSONArray) var0).get(var9), var1, lfm);
                        }
                    }

                    if (((JSONArray) var0).length() >= 5) {
                        Object var10 = ((JSONArray) var0).get(0);
                        Object var11 = ((JSONArray) var0).get(1);
                        ((JSONArray) var0).put(0, ((JSONArray) var0).get(((JSONArray) var0).length() - 1));
                        ((JSONArray) var0).put(((JSONArray) var0).length() - 1, var10);
                        ((JSONArray) var0).put(1, ((JSONArray) var0).get(3));
                        ((JSONArray) var0).put(3, var11);
                    }

                    return var0;
                }

                JSONObject var2 = (JSONObject) var0;
                if (var1 == 0) {
                    if (((JSONObject) var0).has("lfm1")) {
                        var1 = lfm;
                    } else {
                        var1 = ((JSONObject) var0).getInt("lfm5");
                    }
                }

                ArrayList<String> var3 = new ArrayList<>();
                Iterator var4 = var2.keys();

                while (var4.hasNext()) {
                    String var5 = (String) var4.next();
                    var3.add(var5);
                }

                for (String var6 : var3) {
                    if (var2.get(var6).getClass().equals(JSONObject.class) || var2.get(var6).getClass().equals(JSONArray.class) && var2.getJSONArray(var6).length() >= 1 && var2.getJSONArray(var6).get(0).getClass().equals(JSONObject.class) || var2.get(var6).getClass().equals(JSONArray.class) && var2.getJSONArray(var6).length() >= 1 && var2.getJSONArray(var6).get(0).getClass().equals(JSONArray.class)) {
                        var2.put((String) d(var6, var1), decode(var2.get(var6), var1, lfm));
                    } else if (!var6.equals("lfm1") && !var6.equals("lfm2") && !var6.equals("lfm3") && !var6.equals("lfm4") && !var6.equals("lfm5")) {
                        String var7 = (String) d(var6, var1);
                        if (var7.equals("logo")) {
                            var2.put(var7, var2.get(var6));
                        } else {
                            var2.put(var7, d(var2.get(var6), var1));
                        }
                    }

                    var2.remove(var6);
                }
            }

            return var0;
        } catch (Exception | Error var8) {
            return var0;
        }
    }

    private static Object d(Object var0, int var1) throws JSONException {
        int var3;
        if (!(var0 instanceof JSONArray)) {
            if (var0 instanceof Boolean) {
                return var0;
            } else {
                if (!(var0 instanceof String)) {
                    var0 = var0.toString();
                }

                char[] var6 = ((String) var0).toCharArray();

                for (var3 = 0; var3 < var6.length; ++var3) {
                    if (var3 == var6.length - 1 && var6[var3] == ' ') {
                        try {

                            return Integer.valueOf((new String(var6)).substring(0, var3));

                        } catch (Exception | Error e) {

                            try {

                                return Double.valueOf((new String(var6)).substring(0, var3));

                            } catch (Exception | Error ee) {

                                return ((new String(var6)).substring(0, var3));

                            }

                        }
                    }

                    var6[var3] = (char) (var6[var3] - var1);
                }

                return new String(var6);
            }
        } else {
            JSONArray var2 = new JSONArray();

            for (var3 = 0; var3 < ((JSONArray) var0).length(); ++var3) {
                var2.put(d(((JSONArray) var0).get(var3), var1));
            }

            return e(var2);
        }
    }

    @NotNull
    private static Object e(JSONArray var2) throws JSONException {
        if (var2.length() >= 5) {
            Object var7 = var2.get(0);
            Object var4 = var2.get(1);
            var2.put(0, var2.get(var2.length() - 1));
            var2.put(var2.length() - 1, var7);
            var2.put(1, var2.get(3));
            var2.put(3, var4);
        }

        return var2;
    }

    @SuppressWarnings("rawtypes")
    private static Object encode(Object var1, int var2) {
        if (var1 == null || !var1.getClass().equals(JSONObject.class)) {
            return var1;
        } else {

            try {

                JSONObject var3 = ((JSONObject) var1);
                if (var2 == 0) {
                    var2 = 100 + new Random().nextInt(101);
                }

                ArrayList<String> var4 = new ArrayList<>();
                Iterator var5 = var3.keys();

                while (var5.hasNext()) {
                    String var6 = (String) var5.next();
                    var4.add(var6);
                }

                for (String var7 : var4) {
                    if (var3.get(var7) instanceof JSONObject) {
                        var3.put((String) c(var7, var2), encode(var3.get(var7), var2));
                    } else {
                        var3.put((String) c(var7, var2), c(var3.get(var7), var2));
                    }

                    var3.remove(var7);
                }

                var3.put("lfm5", var2);
                return var3;

            } catch (Exception | Error e) {

                return var1;

            }

        }
    }

    private static Object c(Object var1, int var2) throws JSONException {
        if (!(var1 instanceof String) && !(var1 instanceof Number)) {
            if (!(var1 instanceof JSONArray)) {
                return var1;
            } else {
                JSONArray var6 = new JSONArray();

                for (int var7 = 0; var7 < ((JSONArray) var1).length(); ++var7) {
                    var6.put(c(((JSONArray) var1).get(var7), var2));
                }

                return e(var6);
            }
        } else {
            boolean var3 = false;
            if (var1 instanceof Number) {
                var1 = var1.toString();
                var3 = true;
            }

            char[] var4 = ((String) var1).toCharArray();

            for (int var5 = 0; var5 < var4.length; ++var5) {
                var4[var5] = (char) (var4[var5] + var2);
            }

            return var3 ? new String(var4) + " " : new String(var4);
        }
    }

    public static String toUTF8(String line) {

        int index = 0;
        char[] chars = line.toCharArray();

        StringBuilder builder = new StringBuilder();

        while (index < chars.length) {

            char symbol = chars[index++];

            if (symbol == '\\' && index + 4 < chars.length) {

                symbol = chars[index++];
                if (symbol == 'u') {

                    String character = line.substring(index, index + 4);
                    symbol = (char) Integer.parseInt(character, 16);
                    index += 4;

                }

            }

            builder.append(symbol);

        }

        return (builder.toString());

    }

}
