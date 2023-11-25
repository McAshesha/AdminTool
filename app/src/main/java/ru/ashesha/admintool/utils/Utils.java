package ru.ashesha.admintool.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Utils {

    public static final Executor EXECUTOR = Executors.newCachedThreadPool();


    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable ignored) {
        }
    }

}
