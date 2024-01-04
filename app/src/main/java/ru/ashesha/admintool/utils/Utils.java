package ru.ashesha.admintool.utils;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Utils {

    public static final Executor EXECUTOR = Executors.newCachedThreadPool();

    public static final Random RANDOM = new Random();


    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable ignored) {
            ignored.printStackTrace();
        }
    }

    public static String formatInfoPlayer(String msg) {
        return msg.replace("[#D4161F]", "").replace("[#FF8600]", "")
                .replace("[#ff0093]", "").replace("[#9E00FF]", "")
                .replace("mail-", "Почта: ").replace("money-", "Монеты: ")
                .replace("Процент-", "Процент: ").replace("Жалоб-", "Жалоб: ")
                .replace("Похвал-", "Похвал: ");
    }

    public interface Method {
        void apply();
    }

}
