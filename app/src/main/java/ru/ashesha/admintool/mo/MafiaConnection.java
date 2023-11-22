package ru.ashesha.admintool.mo;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ashesha.admintool.mo.packets.Packet;

import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MafiaConnection {

    private final Socket socket;


    public MafiaConnection(String host, String proxyHost, int proxyPort, String proxyLogin, String proxyPassword) {

        IO.Options options = new IO.Options();
        options.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        /*options.proxyLogin = proxyLogin;
        options.proxyPassword = proxyPassword;*/
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(proxyLogin, proxyPassword.toCharArray());
            }
        });
        options.timeout = 30000;
        options.reconnection = false;
        socket = IO.socket(URI.create(host), options);

    }

    public MafiaConnection(String host, String proxyHost, int proxyPort) {

        IO.Options options = new IO.Options();
        options.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        options.timeout = 30000;
        options.reconnection = false;
        socket = IO.socket(URI.create(host), options);

    }

    public MafiaConnection(String host) {

        IO.Options options = new IO.Options();
        options.timeout = 30000;
        options.reconnection = false;
        socket = IO.socket(URI.create(host), options);

    }

    public void sendPacket(Packet packet) {

        Object json = Decoder.encode(packet.convertToJSON());
        if (json == null)
            socket.emit(packet.getName());
        else if (packet.getName().equals("OutFromClan"))
            socket.emit(packet.getName(), json, 1);
        else if (packet.getName().equals("InviteFriendship"))
            socket.emit(packet.getName(), json, 0);
        else if (packet.getName().equals("OnlineFriends"))
            socket.emit(packet.getName(), json, false);
        else socket.emit(packet.getName(), json);

    }

    public <ResponseType extends Packet> void sendPacket(Packet packet, Class<ResponseType> responseClass, PacketListener<ResponseType> response) {

        Object json = Decoder.encode(packet.convertToJSON());

        if (json == null)
            socket.emit(packet.getName(), (Ack) args -> {

                if (args[0] instanceof JSONObject)
                    response.call(Packet.parsePacket(responseClass, (JSONObject) Decoder.decode(args[0])));

                else if (args[0] instanceof JSONArray)
                    response.call(Packet.parsePacket(responseClass, (JSONArray) Decoder.decode(args[0])));

            });
        else socket.emit(packet.getName(), json, (Ack) args -> {

            if (args[0] instanceof JSONObject)
                response.call(Packet.parsePacket(responseClass, (JSONObject) Decoder.decode(args[0])));

            else if (args[0] instanceof JSONArray)
                response.call(Packet.parsePacket(responseClass, (JSONArray) Decoder.decode(args[0])));

        });

    }

    public <PacketType extends Packet> MafiaConnection registerListenerPacket(Class<PacketType> packetClass, PacketListener<PacketType> listener) {

        socket.on(packetClass.getSimpleName(), args -> {

            if (args.length == 0)
                listener.call(Packet.parsePacket(packetClass, (JSONObject) null));

            else if (args[0] instanceof JSONObject)
                listener.call(Packet.parsePacket(packetClass, (JSONObject) Decoder.decode(args[0])));

            else if (args[0] instanceof JSONArray)
                listener.call(Packet.parsePacket(packetClass, (JSONArray) Decoder.decode(args[0])));

            else {
                JSONArray array = new JSONArray();
                for (Object obj : args)
                    array.put(obj);
                listener.call(Packet.parsePacket(packetClass, (JSONArray) Decoder.decode(array)));
            }

        });
        return this;

    }

    public void connect() throws NotConnectedException {

        AtomicBoolean connected = new AtomicBoolean(false);
        AtomicBoolean wait = new AtomicBoolean(true);

        socket.on(Socket.EVENT_CONNECT, args -> connected.set(true))
                .on(Socket.EVENT_CONNECT_ERROR, args -> wait.set(false))
                .on(Socket.EVENT_DISCONNECT, args -> wait.set(false))
                .on(Socket.EVENT_CONNECT_TIMEOUT, args -> wait.set(false));


        socket.connect();

        while (!connected.get() && wait.get()) {

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new NotConnectedException(e);
            }

        }

        socket.off(Socket.EVENT_CONNECT).off(Socket.EVENT_CONNECT_ERROR)
                .off(Socket.EVENT_DISCONNECT).off(Socket.EVENT_CONNECT_TIMEOUT);

        if (!wait.get() || !connected.get())
            throw new NotConnectedException();

    }

    public boolean connected() {
        try {
            return socket.connected();
        } catch (Throwable e) {
            return false;
        }
    }

    public void disconnect() {

        try {

            socket.disconnect();

        } catch (Throwable ignored) {
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public class NotConnectedException extends Exception {
        public NotConnectedException() {
            super();
        }

        public NotConnectedException(String message) {
            super(message);
        }

        public NotConnectedException(String message, Throwable cause) {
            super(message, cause);
        }

        public NotConnectedException(Throwable cause) {
            super(cause);
        }

        protected NotConnectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

}
