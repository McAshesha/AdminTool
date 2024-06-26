package ru.ashesha.admintool.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ashesha.admintool.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;

import static android.net.Uri.parse;

public class Device {

    @SuppressLint("StaticFieldLeak")
    private static Device INSTANCE = null;

    public static Device getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Device();
        return INSTANCE;
    }



    private boolean nowMenuViewHidden = true, nowAdminViewHidden = true;
    private Activity nowActivity;
    private View nowView, nowMenuView, nowAdminView;
    private MediaPlayer media;
    private ViewModelProvider provider;


    private Device() {
    }


    public AlertDialog.Builder createAlertDialog() {
        return new AlertDialog.Builder(nowActivity);
    }

    public View createView(int id) {
        return View.inflate(nowActivity, id, null);
    }


    public void showSnackBar(String text) {
        Snackbar.make(nowView, text, Snackbar.LENGTH_SHORT).show();
    }

    public void showToastBar(String text) {
        Toast.makeText(nowActivity, text, Toast.LENGTH_SHORT).show();
    }

    public void copyTextToClipboard(String textToCopy) {
        ClipboardManager clipboardManager = (ClipboardManager) nowActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null)
            return;
        ClipData clipData = ClipData.newPlainText("text", textToCopy);
        clipboardManager.setPrimaryClip(clipData);
    }



    public String getLoginI() {
        try {
            return Settings.Secure.getString(nowActivity.getContentResolver(), "android_id") + " ! " +
                    Settings.Secure.getString(nowActivity.getContentResolver(), "default_input_method") + " ! " +
                    Settings.Secure.getString(nowActivity.getContentResolver(), "settings_classname") + " ! " +
                    Settings.Secure.getString(nowActivity.getContentResolver(), "allowed_geolocation_origins") +
                    " ! " + Build.ID + " ! " + Build.MODEL + " ! " + Build.SERIAL;
        } catch (Throwable e) {
            return e.toString();
        }
    }

    public String getLoginM1() {
        return getLoginM("wlan0");
    }

    public String getLoginM2() {
        return getLoginM("eth0");
    }

    public String getLoginP1() {
        return getLoginP(true);
    }

    public String getLoginP2() {
        return getLoginP(false);
    }

    private String getLoginM(String str) {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (str == null || networkInterface.getName().equalsIgnoreCase(str)) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null)
                        return "";

                    StringBuilder sb = new StringBuilder();
                    for (byte address : hardwareAddress)
                        sb.append(String.format("%02X:", address));

                    if (sb.length() > 0)
                        sb.deleteCharAt(sb.length() - 1);

                    return sb.toString();
                }
            }
        } catch (Throwable ignored) {
        }
        return "";
    }

    private String getLoginP(boolean z) {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress inetAddress : Collections.list(networkInterface.getInetAddresses())) {
                    if (inetAddress.isLoopbackAddress())
                        continue;

                    String hostAddress = inetAddress.getHostAddress();
                    boolean z2 = hostAddress.indexOf(58) < 0;
                    if (z) {
                        if (z2)
                            return hostAddress;

                    } else if (!z2) {
                        int indexOf = hostAddress.indexOf(37);
                        return indexOf < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, indexOf).toUpperCase();
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return "";
    }




    public DataModel getDataModel() {
        return provider.get(DataModel.class);
    }

    public void loadNowActivity(AppCompatActivity activity)  {
        this.nowActivity = activity;
        media = MediaPlayer.create(nowActivity, R.raw.click);
        provider = new ViewModelProvider(activity);
    }

    public void loadNowView(View view) {
        this.nowView = view;
    }

    public void loadNowMenuView(View view) {
        this.nowMenuView = view;
    }

    public void loadNowAdminView(View view) {
        this.nowAdminView = view;
    }



    public Resources getResources() {
        return nowActivity.getResources();
    }

    public boolean isNowMenuViewHidden() {
        return nowMenuViewHidden;
    }

    public void setNowMenuViewHidden(boolean nowMenuViewHidden) {
        this.nowMenuViewHidden = nowMenuViewHidden;
    }

    public boolean isNowAdminViewHidden() {
        return nowAdminViewHidden;
    }

    public void setNowAdminViewHidden(boolean nowAdminViewHidden) {
        this.nowAdminViewHidden = nowAdminViewHidden;
    }



    public NavController findNavController() {
        return Navigation.findNavController(nowView);
    }

    public NavController findMenuNavController() {
        return Navigation.findNavController(nowMenuView);
    }

    public NavController findAdminNavController() {
        return Navigation.findNavController(nowAdminView);
    }



    public void runOnMainThread(Runnable runnable) {
        nowActivity.runOnUiThread(runnable);
    }

    public void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, parse(url));
        nowActivity.startActivity(intent);
    }


    public void destroy() {
        nowActivity = null;
        nowView = null;
        nowMenuView = null;
        nowAdminView = null;
        media = null;
    }


    public interface OnClickListenerWithSound extends View.OnClickListener {
        @Override
        default void onClick(View v) {
            MediaPlayer media = Device.getInstance().media;
            if (media != null && UserData.getInstance().isRealEnableSoundClick()) {
                if (media.isPlaying())
                    media.stop();
                media.seekTo(300);
                media.start();
            }
            onClickWithSound(v);
        }

        void onClickWithSound(View v);
    }

    public interface OnItemSelectedWithSound extends AdapterView.OnItemSelectedListener {
        @Override
        default void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            MediaPlayer media = Device.getInstance().media;
            if (media != null && UserData.getInstance().isRealEnableSoundClick()) {
                if (media.isPlaying()) {
                    media.pause();
                } else media.start();
                media.seekTo(500);
            }
            onClickWithSound(view, position, id);
        }

        @Override
        default void onNothingSelected(AdapterView<?> parent) {

        }

        void onClickWithSound(View view, int position, long id);
    }

    public interface OnTextChangeListener extends TextWatcher {
        @Override
        default void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        default void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    public static class DataModel extends ViewModel {
        private JSONObject json = new JSONObject();

        public void putInfo(String key, Object info) {
            try {
                if (json.has(key))
                    json.remove(key);
                json.put(key, info);
            } catch (JSONException ignored) {
            }
        }

        public <T> T getInfo(String key) {
            try {
                return (T) json.get(key);
            } catch (JSONException e) {
                return null;
            }
        }

        public void removeInfo(String key) {
            json.remove(key);
        }
    }

}
