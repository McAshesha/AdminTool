package ru.ashesha.admintool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.UserData;


public class MainActivity extends AppCompatActivity {

    /**
     * TODO: Добавить фрагмент декодера + энкодера
     * TODO: Добавить фрагмент сырого подключения с билдером пакетов
     * TODO: Добавить фрагмент топа классики
     * TODO: Добавить фрагменты связанные с районами и кланами !!!
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Device device = Device.getInstance();
        device.loadNowActivity(this);

        findViewById(R.id.contact).setOnClickListener(v -> openTelegramLink());

        SharedPreferences sharedPreferences = getSharedPreferences("AdminData", Context.MODE_PRIVATE);
        UserData data = UserData.getInstance();

        data.setTopLogin(sharedPreferences.getString("topLogin", ""));
        data.setTopPassword(sharedPreferences.getString("topPassword", ""));

        data.setOnlineLogin(sharedPreferences.getString("onlineLogin", ""));
        data.setOnlinePassword(sharedPreferences.getString("onlinePassword", ""));

        data.setAdminLogin(sharedPreferences.getString("adminLogin", ""));
        data.setAdminPassword(sharedPreferences.getString("adminPassword", ""));

        data.setAutomessage(sharedPreferences.getString("automessage", ""));
        data.setEnableAutomessage(Boolean.parseBoolean(sharedPreferences.getString("enableAutomessage", "false")));

        data.setVersion(sharedPreferences.getString("version", ""));
        data.setEnableSoundClick(Boolean.parseBoolean(sharedPreferences.getString("enableSoundClick", "true")));
    }

    private void openTelegramLink() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/mcashesha"));
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("AdminData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        UserData data = UserData.getInstance();

        editor.putString("topLogin", data.getRealTopLogin());
        editor.putString("topPassword", data.getRealTopPassword());

        editor.putString("onlineLogin", data.getRealOnlineLogin());
        editor.putString("onlinePassword", data.getRealOnlinePassword());

        editor.putString("adminLogin", data.getRealAdminLogin());
        editor.putString("adminPassword", data.getRealAdminPassword());

        editor.putString("automessage", data.getRealAutomessage());
        editor.putString("enableAutomessage", Boolean.toString(data.isRealEnableAutomessage()));

        editor.putString("version", data.getRealVersion());
        editor.putString("enableSoundClick", Boolean.toString(data.isRealEnableSoundClick()));

        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Device.getInstance().destroy();
        System.out.println("HUUUUUUUUUUUi");
    }

}
