package ru.ashesha.admintool.mo.packets.client;

public class Golosovanie {
    final String PlayerName;
    final String room;

    public Golosovanie(String playerName) {
        PlayerName = playerName;
        this.room = "Частная классика";
    }
}
