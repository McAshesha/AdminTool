package ru.ashesha.admintool.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Utils {

    public static final Executor EXECUTOR = Executors.newCachedThreadPool();
    public static final Random RANDOM = new Random();
    private static boolean invisibleDeepView;
    @SuppressLint("StaticFieldLeak")
    private static Activity nowActivity;
    @SuppressLint("StaticFieldLeak")
    private static View nowView, nowDeepView;

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable ignored) {
        }
    }

    public static AlertDialog.Builder createAlertDialog() {
        return new AlertDialog.Builder(nowActivity);
    }

    public static View createView(int id) {
        return View.inflate(nowActivity, id, null);
    }


    public static void showSnackBar(String text) {
        Snackbar.make(nowView, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void showToastBar(String text) {
        Toast.makeText(nowActivity, text, Toast.LENGTH_SHORT).show();
    }

    public static void copyTextToClipboard(String textToCopy) {
        ClipboardManager clipboardManager = (ClipboardManager) nowActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null)
            return;
        ClipData clipData = ClipData.newPlainText("text", textToCopy);
        clipboardManager.setPrimaryClip(clipData);
        showToastBar("Скопировано в буфер обмена!");
    }



    public static void setNowActivity(Activity activity) {
        Utils.nowActivity = activity;
    }

    public static void setNowView(View view) {
        Utils.nowView = view;
    }

    public static void setNowDeepView(View view) {
        Utils.nowDeepView = view;
    }

    public static boolean isInvisibleDeepView() {
        return invisibleDeepView;
    }

    public static void setInvisibleDeepView(boolean invisibleDeepView) {
        Utils.invisibleDeepView = invisibleDeepView;
    }

    public static NavController findNavController() {
        return Navigation.findNavController(nowView);
    }

    public static NavController findDeepNavController() {
        return Navigation.findNavController(nowDeepView);
    }

    public static void runOnMainThread(Runnable runnable) {
        nowActivity.runOnUiThread(runnable);
    }

}
