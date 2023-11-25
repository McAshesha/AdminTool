package ru.ashesha.admintool.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import ru.ashesha.admintool.R;

public class Device {

    @SuppressLint("StaticFieldLeak")
    private static Device INSTANCE = null;

    public static Device getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Device();
        return INSTANCE;
    }



    private boolean nowSmallViewHidden = true;
    private Activity nowActivity;
    private View nowView, nowSmallView;
    private MediaPlayer media;


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
        showToastBar("Скопировано в буфер обмена!");
    }



    public void loadNowActivity(Activity activity)  {
        this.nowActivity = activity;
        media = MediaPlayer.create(nowActivity, R.raw.click);
    }

    public void loadNowView(View view) {
        this.nowView = view;
    }

    public void loadNowSmallView(View view) {
        nowSmallView = view;
    }

    public Resources getResources() {
        return nowActivity.getResources();
    }

    public boolean isNowSmallViewHidden() {
        return nowSmallViewHidden;
    }

    public void setNowSmallViewHidden(boolean nowSmallViewHidden) {
        this.nowSmallViewHidden = nowSmallViewHidden;
    }

    public NavController findNavController() {
        return Navigation.findNavController(nowView);
    }

    public NavController findSmallNavController() {
        return Navigation.findNavController(nowSmallView);
    }



    public void runOnMainThread(Runnable runnable) {
        nowActivity.runOnUiThread(runnable);
    }


    public void destroy() {
        nowActivity = null;
        nowView = null;
        nowSmallView = null;
        media = null;
    }


    public static interface OnClickListenerWithSound extends View.OnClickListener {
        @Override
        default void onClick(View v) {
            MediaPlayer media = Device.getInstance().media;
            if (media != null) {
                if (media.isPlaying()) {
                    media.pause();
                } else media.start();
                media.seekTo(500);
            }
            onClickWithSound(v);
        }

        void onClickWithSound(View v);
    }

    public static interface OnTextChangeListener extends TextWatcher {
        @Override
        default void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        default void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

}
